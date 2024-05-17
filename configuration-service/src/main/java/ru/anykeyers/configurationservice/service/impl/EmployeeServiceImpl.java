package ru.anykeyers.configurationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.Employee;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.repository.EmployeeRepository;
import ru.anykeyers.configurationservice.service.EmployeeService;

import java.util.List;

/**
 * Реализация сервиса обработки работников
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ObjectMapper objectMapper;

    private final RemoteUserService remoteUserService;

    private final EmployeeRepository employeeRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<UserDTO> getCarWashEmployees(Long carWashId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        List<Long> employeesIds = employeeRepository.findByConfiguration(configuration).stream()
                .map(Employee::getUserId)
                .toList();
        return remoteUserService.getUsers(employeesIds);
    }

    @Override
    @SneakyThrows
    public void addCarWashEmployee(Long carWashId, Long userId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        Employee employee = Employee.builder()
                .userId(userId)
                .configuration(configuration)
                .build();
        employeeRepository.save(employee);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getUserId(), employee.getConfiguration().getId());
        kafkaTemplate.send(MessageQueue.INVITATION_APPLY, objectMapper.writeValueAsString(employeeDTO));
    }
}

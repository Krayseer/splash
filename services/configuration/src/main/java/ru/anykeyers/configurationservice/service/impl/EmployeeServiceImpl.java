package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.user.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.user.UserDTO;
import ru.anykeyers.commonsapi.remote.RemoteUserService;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.employee.Employee;
import ru.anykeyers.configurationservice.domain.employee.EmployeeMapper;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.repository.EmployeeRepository;
import ru.anykeyers.configurationservice.service.EmployeeService;
import ru.anykeyers.configurationservice.service.EventService;

import java.util.List;

/**
 * Реализация сервиса обработки работников
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EventService eventService;

    private final RemoteUserService remoteUserService;

    private final EmployeeRepository employeeRepository;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<UserDTO> getCarWashEmployees(Long carWashId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        List<String> employees = employeeRepository.findByConfiguration(configuration).stream()
                .map(Employee::getUsername)
                .toList();
        return remoteUserService.getUsers(employees);
    }

    @Override
    public void addCarWashEmployee(Configuration configuration, String username) {
        Employee employee = Employee.builder().username(username).configuration(configuration).build();
        employeeRepository.save(employee);
        EmployeeDTO employeeDTO = EmployeeMapper.toDTO(employee);
        log.info("Add employee to car wash: {}", employee);
        eventService.sendEmployeeApplyEvent(employeeDTO);
    }

    @Override
    public void deleteEmployee(Long carWashId, Long userId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        employeeRepository.deleteByConfigurationAndUserId(configuration, userId);
    }

}

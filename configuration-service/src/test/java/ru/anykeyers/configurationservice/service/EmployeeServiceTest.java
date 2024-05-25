package ru.anykeyers.configurationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.anykeyers.configurationservice.domain.Employee;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.repository.EmployeeRepository;
import ru.anykeyers.configurationservice.service.impl.EmployeeServiceImpl;

import java.util.Optional;

/**
 * Тесты для {@link EmployeeService}
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    /**
     * Тест добавления работника несуществующей автомойке
     */
    @Test
    void addNotExistedCarWashEmployee() {
        Mockito.when(configurationRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                ConfigurationNotFoundException.class, () -> employeeService.addCarWashEmployee(1L, 1L)
        );
    }

    /**
     * Тест добавления работника автомойке
     */
    @Test
    void addCarWashEmployee() {
        Configuration existsConfiguration = Configuration.builder()
                .id(1L)
                .username("test-user")
                .name("Best carwash")
                .description("My car wash is really best")
                .phoneNumber("+799999999")
                .address("Lvov")
                .openTime("08:00")
                .closeTime("23:00")
                .managementProcessOrders(false)
                .build();
        Mockito.when(configurationRepository.findById(1L)).thenReturn(Optional.of(existsConfiguration));

        employeeService.addCarWashEmployee(1L, 2L);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(captor.capture());
        Employee savedEmployee = captor.getValue();
        Assertions.assertEquals(savedEmployee.getUserId(), 2L);
        Assertions.assertEquals(savedEmployee.getConfiguration().getId(), 1L);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(Mockito.eq("invitation-apply"), Mockito.any());
    }

}
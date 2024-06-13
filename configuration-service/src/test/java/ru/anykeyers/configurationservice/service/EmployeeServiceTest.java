package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.configurationservice.domain.employee.Employee;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.repository.EmployeeRepository;
import ru.anykeyers.configurationservice.service.impl.EmployeeServiceImpl;

/**
 * Тесты для {@link EmployeeService}
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EventService eventService;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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

        employeeService.addCarWashEmployee(existsConfiguration, 2L);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(captor.capture());
        Employee savedEmployee = captor.getValue();
        Assertions.assertEquals(savedEmployee.getUserId(), 2L);
        Assertions.assertEquals(savedEmployee.getConfiguration().getId(), 1L);
        Mockito.verify(eventService, Mockito.times(1)).sendEmployeeApplyEvent(Mockito.any());
    }

}
package ru.anykeyers.notificationservice.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.notificationservice.service.NotificationServiceCompound;

/**
 * Тесты для {@link EmployeeProcessor}
 */
@ExtendWith(MockitoExtension.class)
class EmployeeProcessorTest {

    @Mock
    private RemoteUserService remoteUserService;

    @Mock
    private RemoteConfigurationService remoteConfigurationService;

    @Mock
    private NotificationServiceCompound notificationServiceCompound;

    @InjectMocks
    private EmployeeProcessor employeeProcessor;

    @Test
    void processEmployeeApply() {
        Long carWashId = 2L;
        ConfigurationDTO configuration = ConfigurationDTO.builder()
                .id(carWashId)
                .name("car-wash")
                .address("address")
                .username("car-wash-holder")
                .build();
        UserDTO employee = UserDTO.builder()
                .id(1L)
                .build();
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, carWashId);
        UserDTO carWashHolder = UserDTO.builder()
                .id(3L)
                .username("car-wash-holder")
                .build();
        Mockito.when(remoteConfigurationService.getConfiguration(carWashId)).thenReturn(configuration);
        Mockito.when(remoteUserService.getUser(1L)).thenReturn(employee);
        Mockito.when(remoteUserService.getUser("car-wash-holder")).thenReturn(carWashHolder);

        employeeProcessor.processEmployeeApply(employeeDTO);

        Mockito.verify(notificationServiceCompound).notify(employee, Mockito.any());
        Mockito.verify(notificationServiceCompound).notify(carWashHolder, Mockito.any());
    }

}
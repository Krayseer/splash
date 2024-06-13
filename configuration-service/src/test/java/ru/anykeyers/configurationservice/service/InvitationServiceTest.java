package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.invitation.Invitation;
import ru.anykeyers.configurationservice.domain.invitation.InvitationRequest;
import ru.anykeyers.configurationservice.exception.InvitationNotFoundException;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.repository.InvitationRepository;
import ru.anykeyers.configurationservice.service.impl.InvitationServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * Тесты для {@link InvitationService}
 */
@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private RemoteUserService remoteUserService;

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private InvitationServiceImpl invitationService;

    @Captor
    private ArgumentCaptor<Invitation> invitationCaptor;

    /**
     * Тест добавления приглашения пользователю
     */
    @Test
    void addInvitation() {
        InvitationRequest invitationRequest = new InvitationRequest(
                "test-user", 2L, List.of("ROLE_MANAGER")
        );
        Configuration configuration = Configuration.builder().id(2L).build();
        Mockito.when(configurationRepository.findById(2L)).thenReturn(Optional.of(configuration));

        invitationService.addInvitation(invitationRequest);

        Mockito.verify(invitationRepository, Mockito.times(1)).save(invitationCaptor.capture());
        Invitation invitation = invitationCaptor.getValue();
        Assertions.assertEquals(invitation.getUsername(), "test-user");
        Assertions.assertEquals(invitation.getConfiguration().getId(), 2L);
        Assertions.assertEquals(invitation.getRoles(), List.of("ROLE_MANAGER"));
    }

    /**
     * Тест одобрения приглашения
     */
    @Test
    void applyInvitation() {
        Long invitationId = 1L;
        Configuration configuration = Configuration.builder().id(1L).build();
        Invitation invitation = Invitation.builder()
                .id(invitationId)
                .username("test-user")
                .configuration(configuration)
                .roles(List.of("ROLE_WASHER"))
                .build();
        UserDTO user = UserDTO.builder().id(2L).username("test-user").build();
        Mockito.when(invitationRepository.findById(invitationId)).thenReturn(Optional.of(invitation));
        Mockito.when(remoteUserService.getUser("test-user")).thenReturn(user);

        invitationService.applyInvitation(invitationId);

        Mockito.verify(employeeService, Mockito.times(1)).addCarWashEmployee(configuration, 2L);
    }

    /**
     * Тест одобрения несуществующего приглашения
     */
    @Test
    void applyNotExistsInvitation() {
        Long invitationId = 1L;
        Mockito.when(invitationRepository.findById(invitationId)).thenReturn(Optional.empty());

        InvitationNotFoundException exception = Assertions.assertThrows(
                InvitationNotFoundException.class, () -> invitationService.applyInvitation(invitationId)
        );

        Assertions.assertEquals("Invitation with id 1 not found", exception.getMessage());
        Mockito.verify(employeeService, Mockito.never()).addCarWashEmployee(Mockito.any(), Mockito.any());
    }

}
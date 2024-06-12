package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.commonsapi.domain.invitation.InvitationDTO;
import ru.anykeyers.configurationservice.domain.invitation.Invitation;
import ru.anykeyers.configurationservice.exception.InvitationNotFoundException;
import ru.anykeyers.configurationservice.repository.InvitationRepository;
import ru.anykeyers.configurationservice.service.impl.InvitationServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * Тесты для {@link }
 */
@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private EmployeeService employeeService;

    @Captor
    private ArgumentCaptor<Invitation> invitationCaptor;

    @InjectMocks
    private InvitationServiceImpl invitationService;

    /**
     * Тест добавления приглашения пользователю
     */
    @Test
    void addInvitation() {
        InvitationDTO invitationDTO = new InvitationDTO("test-user", 2L, List.of("ROLE_MANAGER"));

        invitationService.addInvitation(invitationDTO);

        Mockito.verify(invitationRepository, Mockito.times(1)).save(invitationCaptor.capture());
        Invitation invitation = invitationCaptor.getValue();
        Assertions.assertEquals(invitation.getUsername(), "test-user");
        Assertions.assertEquals(invitation.getCarWashId(), 2L);
        Assertions.assertEquals(invitation.getRoles(), List.of("ROLE_MANAGER"));
    }

    /**
     * Тест одобрения приглашения
     */
    @Test
    void applyInvitation() {
        Long invitationId = 1L;
        Invitation invitation = Invitation.builder()
                .id(invitationId)
                .username("test-user")
                .carWashId(1L)
                .roles(List.of("ROLE_WASHER"))
                .build();
        Mockito.when(invitationRepository.findById(invitationId)).thenReturn(Optional.of(invitation));

        invitationService.applyInvitation(invitationId);

        Mockito.verify(employeeService, Mockito.times(1)).addCarWashEmployee(1L, invitationId);
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
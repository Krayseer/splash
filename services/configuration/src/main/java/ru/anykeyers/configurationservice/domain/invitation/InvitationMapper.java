package ru.anykeyers.configurationservice.domain.invitation;

import ru.anykeyers.commonsapi.domain.invitation.InvitationDTO;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.configurationservice.domain.Invitation;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationMapper;
import ru.anykeyers.configurationservice.web.dto.InvitationRequest;

import java.util.List;

/**
 * Фабрика по созданию приглашений
 */
public final class InvitationMapper {

    /**
     * Создать приглашение
     *
     * @param invitationRequest данные о приглашении
     */
    public static Invitation toInvitation(InvitationRequest invitationRequest) {
        return Invitation.builder()
                .username(invitationRequest.getUsername())
                .invitationState(InvitationState.SENT)
                .roles(invitationRequest.getRoles())
                .build();
    }

    /**
     * Создать ответ с данными о приглашении
     *
     * @param invitation приглашение
     */
    public static InvitationDTO toDTO(Invitation invitation) {
        return new InvitationDTO(
                invitation.getId(),
                invitation.getUsername(),
                ConfigurationMapper.toSimpleDTO(invitation.getConfiguration()),
                invitation.getRoles(),
                invitation.getInvitationState()
        );
    }

    /**
     * Создать ответ со списком данных о приглашениях
     *
     * @param invitations список приглашений
     */
    public static List<InvitationDTO> toDTO(List<Invitation> invitations) {
        return invitations.stream().map(InvitationMapper::toDTO).toList();
    }

}

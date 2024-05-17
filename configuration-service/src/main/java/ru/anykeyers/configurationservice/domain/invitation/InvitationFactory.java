package ru.anykeyers.configurationservice.domain.invitation;

import ru.anykeyers.commonsapi.domain.dto.InvitationDTO;

import java.util.List;

/**
 * Фабрика по созданию приглашений
 */
public final class InvitationFactory {

    /**
     * Создать приглашение
     *
     * @param invitationDTO данные о приглашении
     */
    public static Invitation createInvitation(InvitationDTO invitationDTO) {
        return Invitation.builder()
                .username(invitationDTO.getUsername())
                .carWashId(invitationDTO.getCarWashId())
                .roles(invitationDTO.getRoles())
                .build();
    }

    /**
     * Создать ответ с данными о приглашении
     *
     * @param invitation приглашение
     */
    public static InvitationDTO createResponse(Invitation invitation) {
        return new InvitationDTO(invitation.getUsername(), invitation.getCarWashId(), invitation.getRoles());
    }

    /**
     * Создать ответ со списком данных о приглашениях
     *
     * @param invitations список приглашений
     */
    public static List<InvitationDTO> createResponse(List<Invitation> invitations) {
        return invitations.stream().map(InvitationFactory::createResponse).toList();
    }

}

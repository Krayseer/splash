package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.invitation.InvitationDTO;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.configurationservice.web.dto.InvitationRequest;

import java.util.List;

/**
 * Сервис обработки приглашений
 */
public interface InvitationService {

    /**
     * Получить список приглашений пользователя
     *
     * @param username имя пользователя
     */
    List<InvitationDTO> getInvitations(String username);

    /**
     * Получить список приглашений автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    List<InvitationDTO> getInvitations(Long carWashId);

    /**
     * Получить список приглашений автомойки
     *
     * @param carWashId         идентификатор автомойки
     * @param invitationState   статус приглашения
     */
    List<InvitationDTO> getInvitations(Long carWashId, InvitationState invitationState);

    /**
     * Добавить приглашение
     *
     * @param invitation данные о приглашении
     */
    void addInvitation(InvitationRequest invitation);

    /**
     * Одобрить приглашение
     *
     * @param invitationId идентификатор приглашения
     */
    void applyInvitation(Long invitationId);

    /**
     * Отклонить приглашение
     *
     * @param id идентификатор приглашения
     */
    void declineInvitation(Long id);

    /**
     * Удалить приглашение
     *
     * @param id идентификатор приглашения
     */
    void deleteInvitation(Long id);

}

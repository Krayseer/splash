package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.InvitationDTO;

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
     * Добавить приглашение
     *
     * @param invitation данные о приглашении
     */
    void addInvitation(InvitationDTO invitation);

    /**
     * Одобрить приглашение
     *
     * @param invitationId идентификатор приглашения
     */
    void applyInvitation(Long invitationId);
}

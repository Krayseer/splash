package ru.anykeyers.configurationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.configurationservice.domain.Invitation;

import java.util.List;

/**
 * DAO для работы с приглашениями
 */
@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    /**
     * Получить список приглашений пользователя
     *
     * @param username имя пользователя
     */
    List<Invitation> findByUsername(String username);

    /**
     * Получить список приглашений автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    List<Invitation> findByConfigurationId(Long carWashId);

    /**
     * Получить список приглашений автомойки
     *
     * @param carWashId         идентификатор автомойки
     * @param invitationState   статус приглашения
     */
    List<Invitation> findByConfigurationIdAndInvitationState(Long carWashId, InvitationState invitationState);
}

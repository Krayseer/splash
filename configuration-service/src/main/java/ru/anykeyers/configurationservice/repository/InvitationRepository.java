package ru.anykeyers.configurationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.configurationservice.domain.entity.Invitation;

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
    List<Invitation> findByCarWashId(Long carWashId);

}

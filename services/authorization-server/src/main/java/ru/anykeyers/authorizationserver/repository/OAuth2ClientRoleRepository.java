package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.entity.OAuth2ClientRole;

/**
 * DAO для работы с OAuth2 ролями клиента
 */
public interface OAuth2ClientRoleRepository extends JpaRepository<OAuth2ClientRole, Long> {

    /**
     * Найти OAuth2 роль
     *
     * @param clientRegistrationId  идентификатор приложения
     * @param roleCode              код роли
     */
    OAuth2ClientRole findByClientRegistrationIdAndRoleCode(String clientRegistrationId, String roleCode);

}

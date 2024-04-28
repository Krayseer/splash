package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.entity.OAuth2ClientRole;

public interface OAuth2ClientRoleRepository extends JpaRepository<OAuth2ClientRole, Long> {

    OAuth2ClientRole findByClientRegistrationIdAndRoleCode(String clientRegistrationId, String roleCode);

}

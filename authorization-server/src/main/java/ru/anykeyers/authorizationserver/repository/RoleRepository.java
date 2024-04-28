package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleCode(String roleCode);

}

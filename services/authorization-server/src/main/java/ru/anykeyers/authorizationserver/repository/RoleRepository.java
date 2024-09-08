package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.entity.Role;

import java.util.List;

/**
 * DAO для работы с ролями
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Получить роль
     *
     * @param roleCode код (строковое представление строки)
     */
    Role findByRoleCode(String roleCode);

    /**
     * Получить список ролей
     *
     * @param roleCodes список кодов
     */
    List<Role> findByRoleCodeIn(List<String> roleCodes);

}

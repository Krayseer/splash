package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.user.User;

/**
 * DAO для работы с пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Найти пользователя
     *
     * @param username имя пользователя
     */
    User findUserByUsername(String username);

}

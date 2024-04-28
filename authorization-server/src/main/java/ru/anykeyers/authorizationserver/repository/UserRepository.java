package ru.anykeyers.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anykeyers.authorizationserver.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

}

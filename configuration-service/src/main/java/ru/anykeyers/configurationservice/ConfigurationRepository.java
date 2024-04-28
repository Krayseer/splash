package ru.anykeyers.configurationservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.configurationservice.domain.Configuration;

import java.util.Optional;

/**
 * DAO для работы с конфигурациями автомоек
 */
@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    /**
     * Получить конфигурацию автомойки
     *
     * @param username имя пользователя хозяина автомойки
     */
    Optional<Configuration> findByUsername(String username);

}

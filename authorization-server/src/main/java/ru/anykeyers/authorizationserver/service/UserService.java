package ru.anykeyers.authorizationserver.service;

import ru.anykeyers.authorizationserver.domain.UserRequest;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.util.List;

/**
 * Сервис обработки пользователей
 */
public interface UserService {

    /**
     * Получить информацию о пользователе
     *
     * @param username имя пользователя
     */
    UserDTO getUser(String username);

    /**
     * Зарегистрировать пользователя
     *
     * @param userRequest данные для регистрации пользователя
     */
    void registerUser(UserRequest userRequest);

    /**
     * Установить роли пользователю
     *
     * @param userId    идентификатор пользователя
     * @param roles     список ролей
     */
    void setUserRoles(Long userId, List<String> roles);
}

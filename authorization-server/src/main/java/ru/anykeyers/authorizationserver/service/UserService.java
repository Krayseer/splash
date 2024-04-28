package ru.anykeyers.authorizationserver.service;

import ru.anykeyers.authorizationserver.domain.UserRequest;
import ru.anykeyers.commonsapi.dto.UserDTO;

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
}

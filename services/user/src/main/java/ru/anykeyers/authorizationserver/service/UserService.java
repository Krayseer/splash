package ru.anykeyers.authorizationserver.service;

import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.web.dto.UserRegisterRequest;
import ru.anykeyers.commonsapi.domain.user.User;
import ru.anykeyers.commonsapi.domain.user.UserInfo;

import java.util.UUID;

/**
 * Сервис обработки пользователей
 */
public interface UserService {

    /**
     * Получить пользователя по id
     *
     * @param id идентификатор пользователя
     */
    User getUser(UUID id);

    /**
     * Получить пользователя
     *
     * @param username имя пользователя
     */
    User getUser(String username);

    /**
     * Добавить пользователя
     *
     * @param userRegisterRequest запрос с данными о пользователе
     */
    void addUser(UserRegisterRequest userRegisterRequest);

    /**
     * Обновить пользователя
     *
     * @param user      пользователь
     * @param userInfo  запрос с обновленными данными о пользователе
     */
    void updateUser(User user, UserInfo userInfo);

    /**
     * Удалить пользователя
     *
     * @param id идентификатор пользователя
     */
    void deleteUser(UUID id);

    void addPhoto(User user, MultipartFile photo);

}

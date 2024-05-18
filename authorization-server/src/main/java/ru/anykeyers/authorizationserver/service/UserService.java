package ru.anykeyers.authorizationserver.service;

import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.domain.user.UserRequest;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.UserSettingDTO;

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
     * Получить информацию о пользователе
     *
     * @param id идентификатор пользователя
     */
    UserDTO getUser(Long id);

    /**
     * Получить данные о пользователях
     *
     * @param userIds список идентификаторов пользователей
     */
    List<UserDTO> getUsers(List<Long> userIds);

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

    /**
     * Добавить фотографию пользователю
     *
     * @param username  имя пользователя
     * @param photo     фотография
     */
    void addPhoto(String username, MultipartFile photo);

    /**
     * Установить настройки пользователю
     *
     * @param username      имя пользователя
     * @param userSetting   настройки
     */
    void setUserSetting(String username, UserSettingDTO userSetting);
}

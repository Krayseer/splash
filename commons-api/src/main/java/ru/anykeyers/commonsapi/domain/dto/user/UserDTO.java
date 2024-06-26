package ru.anykeyers.commonsapi.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Ответ с данными о пользователе
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * Идентификатор пользователя
     */
    private Long id;

    /**
     * Имя
     */
    private String name;

    /**
     * Фамилия
     */
    private String surname;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Почта
     */
    private String email;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * URL фотографии аккаунта
     */
    private String photoUrl;

    /**
     * Время создания аккаунта
     */
    private String createdAt;

    /**
     * Список ролей
     */
    private List<String> roles;

    /**
     * Настройки пользователя
     */
    private UserSettingDTO userSetting;

}

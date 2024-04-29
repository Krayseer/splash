package ru.anykeyers.authorizationserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос с данными о пользователе
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    /**
     * Имя
     */
    private String name;

    /**
     * Фамилия
     */
    private String surname;

    /**
     * Роль
     */
    private String role;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Пароль
     */
    private String password;

    /**
     * Почта
     */
    private String email;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * URL фотографии
     */
    private String photoUrl;

}
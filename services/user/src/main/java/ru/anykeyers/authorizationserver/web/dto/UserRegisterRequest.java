package ru.anykeyers.authorizationserver.web.dto;

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
public class UserRegisterRequest {
    /**
     * Имя пользователя
     */
    private String username;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Фамилия
     */
    private String lastName;
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
}

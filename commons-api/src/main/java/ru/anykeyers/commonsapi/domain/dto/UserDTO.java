package ru.anykeyers.commonsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

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
     * Время создания аккаунта
     */
    private String createdAt;

}

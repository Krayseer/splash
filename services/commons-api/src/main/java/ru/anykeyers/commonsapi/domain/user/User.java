package ru.anykeyers.commonsapi.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Пользователь
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Идентификатор
     */
    private UUID id;
    /**
     * Имя пользователя
     */
    private String username;
    /**
     * Данные пользователя
     */
    private UserInfo userInfo;
    /**
     * Дата создания
     */
    private long createdTimestamp;
}

package ru.anykeyers.notificationservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Push уведомление
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Тема
     */
    private String subject;

    /**
     * Сообщение
     */
    private String message;

    /**
     * Время создания
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

}

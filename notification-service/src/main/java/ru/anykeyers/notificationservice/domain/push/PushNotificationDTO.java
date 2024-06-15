package ru.anykeyers.notificationservice.domain.push;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными об push уведомлении
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationDTO {

    /**
     * Идентификатор
     */
    private Long id;

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
    private String createdAt;

    public PushNotificationDTO(PushNotification pushNotification) {
        this.id = pushNotification.getId();
        this.subject = pushNotification.getSubject();
        this.message = pushNotification.getMessage();
        this.createdAt = pushNotification.getCreatedAt().toString();
    }

}

package ru.anykeyers.notificationservice.domain.push;

import ru.anykeyers.notificationservice.domain.Notification;

import java.time.Instant;

/**
 * Маппер для push уведомлений
 */
public class PushNotificationMapper {

    /**
     * Создать push уведомление на базе уведомления
     *
     * @param username      имя пользователя, кому принадлежнит уведомление
     * @param notification  уведомление
     */
    public static PushNotification toPushNotification(String username, Notification notification) {
        return PushNotification.builder()
                .username(username)
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать DTO с данными о push уведомлении
     *
     * @param pushNotification push уведомление
     */
    public static PushNotificationDTO toDTO(PushNotification pushNotification) {
        return new PushNotificationDTO(
                pushNotification.getId(),
                pushNotification.getSubject(),
                pushNotification.getMessage(),
                pushNotification.getCreatedAt().toString()
        );
    }

}

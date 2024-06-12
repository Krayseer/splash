package ru.anykeyers.notificationservice.service;

import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserSettingDTO;
import ru.anykeyers.notificationservice.domain.Notification;

/**
 * Сервис отправки уведомлений
 */
public interface NotificationService {

    /**
     * Отправить уведомление
     *
     * @param user          пользователь, которому нужно отправить уведомление
     * @param notification  уведомление
     */
    void notify(UserDTO user, Notification notification);

    /**
     * Поддерживается ли отправка уведомлений
     *
     * @param userSetting настройки пользователя
     */
    boolean supports(UserSettingDTO userSetting);

}

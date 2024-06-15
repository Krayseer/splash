package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserSettingDTO;
import ru.anykeyers.notificationservice.domain.Notification;
import ru.anykeyers.notificationservice.NotificationRepository;
import ru.anykeyers.notificationservice.domain.push.PushNotification;
import ru.anykeyers.notificationservice.domain.push.PushNotificationDTO;
import ru.anykeyers.notificationservice.domain.push.PushNotificationMapper;
import ru.anykeyers.notificationservice.service.NotificationService;

import java.util.List;

/**
 * Сервис обработки push-уведомлений
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;

    /**
     * Получить список push-уведомлений
     *
     * @param username имя пользователя
     */
    public List<PushNotificationDTO> getNotifications(String username) {
        return notificationRepository.findByUsername(username).stream().map(PushNotificationMapper::toDTO).toList();
    }

    @Override
    public void notify(UserDTO user, Notification notification) {
        PushNotification pushNotification = PushNotificationMapper.toPushNotification(user.getUsername(), notification);
        notificationRepository.save(pushNotification);
        log.info("Created notification: {}", pushNotification);
    }

    @Override
    public boolean supports(UserSettingDTO userSetting) {
        return userSetting.isPushEnabled();
    }

    /**
     * Удалить push уведомление
     *
     * @param pushId идентификатор уведомления
     */
    public void deleteNotification(long pushId) {
        notificationRepository.deleteById(pushId);
        log.info("Deleted notification: {}", pushId);
    }

}

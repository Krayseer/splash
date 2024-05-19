package ru.anykeyers.notificationservice.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.UserSettingDTO;
import ru.anykeyers.notificationservice.domain.Notification;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Комплекс сервисов отправки уведомлений
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceCompound {

    private final ExecutorService executorService;

    private final List<NotificationService> notificationServices;

    /**
     * Отправить уведомление пользователю
     *
     * @param user          пользователь
     * @param notification  уведомление
     */
    public void notify(UserDTO user, Notification notification) {
        for (NotificationService service : getServices(user.getUserSetting())) {
            executorService.execute(() -> service.notify(user, notification));
        }
    }

    private List<NotificationService> getServices(UserSettingDTO userSetting) {
        return notificationServices.stream()
                .filter(service -> service.supports(userSetting))
                .toList();
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}

package ru.anykeyers.notificationservice.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.notificationservice.domain.Notification;
import ru.anykeyers.notificationservice.service.NotificationServiceCompound;

/**
 * Сервис отправки уведомлений для заказов
 */
@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private final NotificationServiceCompound notificationServiceCompound;

    private final RemoteUserService remoteUserService;

    private final RemoteConfigurationService remoteConfigurationService;

    /**
     * Уведомление, что был создан новый заказ
     */
    public void processOrderCreate(OrderDTO order) {
        UserDTO user = remoteUserService.getUser(order.getUsername());
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(order.getCarWashId());
        String message = String.format("""
                        Ваш заказ успешно принят.
                        Автомойка: %s
                        Бокс: %s
                        Время: %s
                        """, configurationDTO.getName(), order.getBoxId(), order.getStartTime());
        Notification notification = new Notification("Новый заказ", message);
        notificationServiceCompound.notify(user, notification);
    }

}

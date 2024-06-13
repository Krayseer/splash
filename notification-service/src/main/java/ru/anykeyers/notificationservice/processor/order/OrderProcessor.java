package ru.anykeyers.notificationservice.processor.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
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
        notifyUser(order, "Новый заказ", this::createOrderMessage);
    }

    /**
     * Уведомление, что был назначен работник на заказ
     *
     * @param order данные о заказе
     */
    public void processOrderEmployeeApply(OrderDTO order) {
        notifyUser(order, "Назначение работника на заказ", this::employeeAssignmentMessage);
    }

    /**
     * Уведомление, что заказ был удален
     *
     * @param order данные о заказе
     */
    public void processOrderDelete(OrderDTO order) {
        notifyUser(order, "Удаление заказа", this::deleteOrderMessage);
    }

    private void notifyUser(OrderDTO order, String notificationTitle, OrderMessageCreator messageCreator) {
        UserDTO user = remoteUserService.getUser(order.getUsername());
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(order.getCarWashId());
        String message = messageCreator.createMessage(configurationDTO, order);
        Notification notification = new Notification(notificationTitle, message);
        notificationServiceCompound.notify(user, notification);
    }

    private String createOrderMessage(ConfigurationDTO configurationDTO, OrderDTO order) {
        return String.format("""
                Ваш заказ успешно принят.
                Автомойка: %s
                Бокс: %s
                Время: %s
                """, configurationDTO.getName(), order.getBoxId(), order.getStartTime());
    }

    private String employeeAssignmentMessage(ConfigurationDTO configurationDTO, OrderDTO order) {
        return String.format("""
                Вам назначен работник на заказ!
                Ожидаем вас по адресу: %s.
                Время: %s
                """, configurationDTO.getAddress(), order.getStartTime());
    }

    private String deleteOrderMessage(ConfigurationDTO configurationDTO, OrderDTO orderDTO) {
        return String.format("""
                Ваш заказ №%s был удален.
                Автомойка: %s.
                Время: %s
                """, orderDTO.getId(), configurationDTO.getAddress(), orderDTO.getStartTime());
    }

}

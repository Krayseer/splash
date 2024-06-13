package ru.anykeyers.notificationservice.processor.order;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;

/**
 * Создатель сообщений о заказах
 */
@FunctionalInterface
public interface OrderMessageCreator {

    /**
     * Создать сообщение
     *
     * @param configuration данные об автомойке
     * @param order         данные о заказе
     */
    String createMessage(ConfigurationDTO configuration, OrderDTO order);

}

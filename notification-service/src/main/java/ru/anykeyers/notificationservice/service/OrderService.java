package ru.anykeyers.notificationservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

/**
 * Сервис отправки уведомлений для заказов
 */
public interface OrderService {

    /**
     * Уведомление, что был создан новый заказ
     */
    void notifyOrderCreate(OrderDTO order);

}

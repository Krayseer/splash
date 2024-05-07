package ru.anykeyers.businessorderservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

/**
 * Сервис обработки заказов
 */
public interface OrderService {

    /**
     * Обработать заказ
     *
     * @param order данные о заказе
     */
    void processOrder(OrderDTO order);

}

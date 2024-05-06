package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.dto.OrderResponse;

/**
 * Сервис обработки заказов
 */
public interface OrderService {

    /**
     * Получить информацию о заказе
     *
     * @param id идентификатор заказа
     */
    OrderResponse getOrder(Long id);

}

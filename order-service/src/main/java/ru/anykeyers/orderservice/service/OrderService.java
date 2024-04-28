package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.OrderDTO;
import ru.anykeyers.orderservice.domain.OrderResponse;

/**
 * Сервис обработки заказов
 */
public interface OrderService {

    /**
     * Получить информацию о заказе
     *
     * @param username имя пользователя
     */
    OrderResponse getOrder(String username);

    /**
     * Сохранить заказ
     *
     * @param username  имя пользователя
     * @param orderDTO  данные о заказе
     */
    void saveOrder(String username, OrderDTO orderDTO);

}

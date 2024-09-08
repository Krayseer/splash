package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.OrderRequest;

import java.util.List;

/**
 * Сервис обработки заказов для пользователей
 */
public interface UserOrderService {

    /**
     * Получить список активных заказов
     *
     * @param username имя пользователя
     */
    List<Order> getActiveOrders(String username);

    /**
     * Получить список завершенных заказов
     *
     * @param username имя пользователя
     */
    List<Order> getProcessedOrders(String username);

    /**
     * Создать заказ
     *
     * @param username      имя пользователя
     * @param orderRequest  данные о заказе
     */
    Order saveOrder(String username, OrderRequest orderRequest);

    /**
     * Удалить заказ
     *
     * @param username  имя пользователя
     * @param orderId   идентификатор заказа
     */
    void deleteOrder(String username, Long orderId);

}

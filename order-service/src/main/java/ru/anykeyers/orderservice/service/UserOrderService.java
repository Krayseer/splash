package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.dto.OrderRequest;
import ru.anykeyers.orderservice.domain.dto.OrderResponse;

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
    List<OrderResponse> getActiveOrders(String username);

    /**
     * Получить список завершенных заказов
     *
     * @param username имя пользователя
     */
    List<OrderResponse> getProcessedOrders(String username);

    /**
     * Создать заказ
     *
     * @param username      имя пользователя
     * @param orderRequest  данные о заказе
     */
    OrderResponse saveOrder(String username, OrderRequest orderRequest);

}

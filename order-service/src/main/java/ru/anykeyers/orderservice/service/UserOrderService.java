package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.order.FullOrderDTO;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

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
    List<FullOrderDTO> getActiveOrders(String username);

    /**
     * Получить список завершенных заказов
     *
     * @param username имя пользователя
     */
    List<FullOrderDTO> getProcessedOrders(String username);

    /**
     * Создать заказ
     *
     * @param username      имя пользователя
     * @param orderRequest  данные о заказе
     */
    OrderDTO saveOrder(String username, OrderRequest orderRequest);

}

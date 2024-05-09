package ru.anykeyers.businessorderservice.service;

import ru.anykeyers.businessorderservice.domain.BusinessOrderRequest;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

import java.util.List;

/**
 * Сервис обработки заказов
 */
public interface OrderService {

    /**
     * Получить список всех заказов работника
     */
    List<OrderDTO> getOrders(String username);

    /**
     * Обработать заказ
     *
     * @param order данные о заказе
     */
    void processOrder(BusinessOrderRequest order);

}

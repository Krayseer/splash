package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.order.OrderState;
import ru.anykeyers.orderservice.domain.Order;

import java.time.Instant;
import java.util.List;

/**
 * Сервис обработки заказов автомойки
 */
public interface CarWashOrderService {

    /**
     * Получить список заказов
     *
     * @param carWashId идентификатор автомойки
     * @param date      дата, за которую нужно получить список заказов
     */
    List<Order> getOrders(Long carWashId, Instant date);

    /**
     * Получить список заказов, ожидающих обработки
     *
     * @param carWashId идентификатор автомойки
     */
    List<Order> getWaitConfirmOrders(Long carWashId);

    /**
     * Получить количество заказов
     *
     * @param carWashId     идентификатор автомойки
     * @param orderState    статус заказа
     */
    int getOrdersCount(Long carWashId, OrderState orderState);

}

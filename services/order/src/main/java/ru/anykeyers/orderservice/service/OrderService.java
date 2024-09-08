package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.Order;

import java.util.List;


/**
 * Сервис обработки заказов
 */
public interface OrderService extends UserOrderService, CarWashOrderService {

    /**
     * Получить информацию о заказе
     *
     * @param id идентификатор заказа
     */
    Order getOrder(Long id);

    /**
     * Получить информацию о списке заказов
     *
     * @param orderIds идентификаторы заказов
     */
    List<Order> getOrders(List<Long> orderIds);

    /**
     * Обработать назначение работника заказу
     */
    void applyOrderEmployee(Long orderId);

    /**
     * Обработать удаление работника с заказа
     *
     * @param orderId идентификатор заказа
     */
    void disappointEmployeeFromOrder(long orderId);

    /**
     * Удалить заказ
     *
     * @param orderId идентификатор заказа
     */
    void deleteOrder(Long orderId);

}

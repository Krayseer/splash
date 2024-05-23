package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.order.FullOrderDTO;


/**
 * Сервис обработки заказов
 */
public interface OrderService extends UserOrderService, CarWashOrderService {

    /**
     * Получить информацию о заказе
     *
     * @param id идентификатор заказа
     */
    FullOrderDTO getOrder(Long id);

    /**
     * Удалить заказ
     *
     * @param orderId идентификатор заказа
     */
    void deleteOrder(String orderId);

    /**
     * Обработать назначение работника заказу
     */
    void applyOrderEmployee(Long orderId);
}

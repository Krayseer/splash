package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

/**
 * Сервис обработки заказов
 */
public interface OrderService extends UserOrderService, CarWashOrderService {

    /**
     * Получить информацию о заказе
     *
     * @param id идентификатор заказа
     */
    OrderDTO getOrder(Long id);

    /**
     * Обработать назначение работника заказу
     */
    void applyOrderEmployee(Long orderId);

}

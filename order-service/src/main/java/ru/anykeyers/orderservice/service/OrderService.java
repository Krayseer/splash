package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;

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
    FullOrderDTO getOrder(Long id);

    /**
     * Получить информацию о списке заказов
     *
     * @param orderIds идентификаторы заказов
     */
    List<OrderDTO> getOrders(List<Long> orderIds);

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
    void deleteOrder(String orderId);
}

package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.domain.dto.FullOrderDTO;

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
    List<FullOrderDTO> getOrders(Long carWashId, Instant date);

    /**
     * Получить список заказов, ожидающих обработки
     *
     * @param carWashId идентификатор автомойки
     */
    List<FullOrderDTO> getWaitConfirmOrders(Long carWashId);

    /**
     * Получить количество заказов
     *
     * @param carWashId     идентификатор автомойки
     * @param orderState    статус заказа
     */
    int getOrdersCount(Long carWashId, OrderState orderState);

}

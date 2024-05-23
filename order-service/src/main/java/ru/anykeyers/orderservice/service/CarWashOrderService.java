package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;

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
    List<OrderDTO> getOrders(Long carWashId, Instant date);

    /**
     * Получить список заказов, ожидающих обработки
     *
     * @param carWashId идентификатор автомойки
     */
    List<FullOrderDTO> getWaitConfirmOrders(Long carWashId);

    /**
     * Получить количество заказов ожидающих обработки
     *
     * @param carWashId идентификатор автомойки
     */
    int getWaitConfirmOrdersCount(Long carWashId);

    /**
     * Получить количество активных заказов
     *
     * @param carWashId идентификатор автомойки
     */
    int getActiveOrdersCount(Long carWashId);

    /**
     * Получить количество заказов, находящихся в обработке
     *
     * @param carWashId идентификатор автомойки
     */
    int getProcessingOrdersCount(Long carWashId);

    /**
     * Получить количество обработаных заказов
     *
     * @param carWashId идентификатор автомойки
     */
    int getProcessedOrdersCount(Long carWashId);

}

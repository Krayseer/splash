package ru.anykeyers.orderservice.service;

/**
 * Сервис обработки заказов автомойки
 */
public interface CarWashOrderService {

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

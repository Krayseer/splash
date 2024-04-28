package ru.anykeyers.orderservice.domain;

/**
 * Статус заказа
 */
public enum OrderStatus {

    /**
     * Создан
     */
    CREATED,
    /**
     * Находится в обработке
     */
    PROCESSING,
    /**
     * Обработан
     */
    PROCESSED

}

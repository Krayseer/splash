package ru.anykeyers.commonsapi.domain;

/**
 * Состояние заказа
 */
public enum OrderState {

    /**
     * Ожидает одобрения
     */
    WAIT_CONFIRM,
    /**
     * Ожидает обработки
     */
    WAIT_PROCESS,
    /**
     * Находится в обработке
     */
    PROCESSING,
    /**
     * Обработан
     */
    PROCESSED

}

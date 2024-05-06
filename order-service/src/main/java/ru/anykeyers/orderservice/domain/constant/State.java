package ru.anykeyers.orderservice.domain.constant;

/**
 * Статус
 */
public enum State {

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

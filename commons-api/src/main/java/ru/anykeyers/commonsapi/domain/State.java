package ru.anykeyers.commonsapi.domain;

/**
 * Статус
 */
public enum State {

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

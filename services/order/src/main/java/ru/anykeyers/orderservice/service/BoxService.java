package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.Interval;

import java.time.Instant;
import java.util.Set;

/**
 * Сервис по обработке боксов
 */
public interface BoxService {

    /**
     * Получить идентификатор бокса для заказа, в зависимости от периода заказа
     *
     * @param carWashId идентификатор автомойки
     * @param interval период заказа
     */
    Long getBoxForOrder(Long carWashId, Interval interval);

    /**
     * Получить список свободных отрезков для заказов
     *
     * @param carWashId идентификатор автомойки
     * @param date      дата
     */
    Set<Interval> getOrderFreeTimes(Long carWashId, Instant date);

}

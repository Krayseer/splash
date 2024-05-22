package ru.anykeyers.orderservice.service;

import ru.anykeyers.orderservice.domain.time.TimeRange;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Сервис по обработке боксов
 */
public interface BoxService {

    /**
     * Получить идентификатор бокса для заказа, в зависимости от периода заказа
     *
     * @param carWashId идентификатор автомойки
     * @param timeRange период заказа
     */
    Optional<Long> getBoxForOrder(Long carWashId, TimeRange timeRange);

    /**
     * Получить список свободных отрезков для заказов
     *
     * @param carWashId идентификатор автомойки
     * @param date      дата
     */
    List<TimeRange> getOrderFreeTimes(Long carWashId, Instant date);

}

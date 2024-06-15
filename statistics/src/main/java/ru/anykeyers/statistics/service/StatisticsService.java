package ru.anykeyers.statistics.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.statistics.domain.metric.CarWashMetric;

import java.util.List;

/**
 * Сервис статистики
 */
public interface StatisticsService {

    /**
     * Получить статистику по автомойке
     *
     * @param carWashId идентификатор автомойки
     */
    List<CarWashMetric> getStatistics(Long carWashId);

    /**
     * Собрать метрики по заказу
     *
     * @param orderDTO данные о заказе
     */
    void processOrder(OrderDTO orderDTO);

}

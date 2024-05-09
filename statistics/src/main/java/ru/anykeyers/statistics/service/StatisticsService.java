package ru.anykeyers.statistics.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.statistics.domain.StatisticsDTO;

import java.util.List;

/**
 * Сервис статистики
 */
public interface StatisticsService {

    /**
     * Получить статистику
     *
     * @param carWashId идентификатор автомойки
     */
    StatisticsDTO getStatistics(Long carWashId);

    /**
     * Собрать метрики по заказу
     *
     * @param orderDTO данные о заказе
     */
    void processOrder(OrderDTO orderDTO);

}

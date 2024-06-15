package ru.anykeyers.statistics.service.batch.processor.order;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.statistics.service.batch.processor.BatchProcessor;

/**
 * Пакетная обработка заказов
 */
public interface OrderBatchProcessor extends BatchProcessor {

    /**
     * Обработать заказ
     *
     * @param order заказ
     */
    void process(OrderDTO order);

}

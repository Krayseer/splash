package ru.anykeyers.statistics.service.batch.processor.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.statistics.domain.metric.OrderMetric;
import ru.anykeyers.statistics.repository.OrderMetricRepository;

/**
 * Пакетная обработка заказов автомойки
 */
@Slf4j
@Component
public class CarWashOrderBatchProcessor extends BaseOrderBatchProcessor<OrderMetric> {

    public CarWashOrderBatchProcessor(OrderMetricRepository orderMetricRepository) {
        super(orderMetricRepository, OrderMetric::new);
    }

    @Override
    public void process(OrderDTO order) {
        OrderMetric orderMetric = getMetric(order.getCarWashId());
        orderMetric.setCount(orderMetric.getCount() + 1);
    }

}

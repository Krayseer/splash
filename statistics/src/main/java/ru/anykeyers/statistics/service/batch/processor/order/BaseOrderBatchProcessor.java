package ru.anykeyers.statistics.service.batch.processor.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.anykeyers.statistics.domain.metric.CarWashMetric;
import ru.anykeyers.statistics.repository.CarWashMetricRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseOrderBatchProcessor<T extends CarWashMetric> implements OrderBatchProcessor {

    private final Map<Long, T> metricByCarWashId = new ConcurrentHashMap<>();

    private final CarWashMetricRepository<T> carWashMetricRepository;

    private final Supplier<T> carWashMetricCreator;

    protected T getMetric(Long carWashId) {
        if (!metricByCarWashId.containsKey(carWashId)) {
            T existsMetric = carWashMetricRepository.findByCarWashId(carWashId);
            metricByCarWashId.put(carWashId, existsMetric == null ? carWashMetricCreator.get() : existsMetric);
        }
        return metricByCarWashId.get(carWashId);
    }

    @Override
    public Runnable getBatchProcessTask() {
        return () -> {
            log.info("Save batch in DB: {}", metricByCarWashId.size());
            carWashMetricRepository.saveAll(metricByCarWashId.values());
            metricByCarWashId.clear();
        };
    }
}

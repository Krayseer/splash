package ru.anykeyers.statistics.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.statistics.domain.ServiceMetric;
import ru.anykeyers.statistics.ServicesRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пакетная обработка статистических данных о заказах
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceBatchProcessor {

    private final Map<Long, ServiceMetric> servicesByCarWashId = new ConcurrentHashMap<>();

    private final ServicesRepository servicesRepository;

    private final AtomicInteger orderCount = new AtomicInteger(0);

    /**
     * Добавить метрики по услугам в статистику
     *
     * @param carWashId идентификатор автомойки
     * @param services  список услуг
     */
    public void addServicesMetric(Long carWashId, List<ServiceDTO> services) {
        if (!servicesByCarWashId.containsKey(carWashId)) {
            ServiceMetric serviceMetric = servicesRepository.findByCarWashId(carWashId);
            servicesByCarWashId.put(carWashId, serviceMetric == null ? new ServiceMetric() : serviceMetric);
        }
        ServiceMetric serviceMetric = servicesByCarWashId.get(carWashId);
        serviceMetric.setSum(serviceMetric.getSum() + services.stream().mapToInt(ServiceDTO::getPrice).sum());
        serviceMetric.setCount(serviceMetric.getCount() + services.size());
        checkCache();
    }

    /**
     * Принудительное сохранение пакета в БД
     */
    public void forceUpdate() {
        log.info("Save batch in DB: {}", servicesByCarWashId.size());
        orderCount.set(0);
        servicesRepository.saveAll(servicesByCarWashId.values());
        servicesByCarWashId.clear();
    }

    private void checkCache() {
        if (orderCount.incrementAndGet() != 100) {
            return;
        }
        forceUpdate();
    }

}

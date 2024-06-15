package ru.anykeyers.statistics.service.batch.processor.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.statistics.domain.metric.ServiceMetric;
import ru.anykeyers.statistics.repository.ServiceMetricRepository;

import java.util.List;

/**
 * Пакетная обработка услуг заказов
 */
@Slf4j
@Component
public class ServiceBatchProcessor extends BaseOrderBatchProcessor<ServiceMetric> {

    private final RemoteServicesService remoteServicesService;

    public ServiceBatchProcessor(ServiceMetricRepository serviceMetricRepository,
                                 RemoteServicesService remoteServicesService) {
        super(serviceMetricRepository, ServiceMetric::new);
        this.remoteServicesService = remoteServicesService;
    }

    @Override
    public void process(OrderDTO order) {
        List<ServiceDTO> services = remoteServicesService.getServices(order.getServiceIds());
        ServiceMetric serviceMetric = getMetric(order.getCarWashId());
        serviceMetric.setSum(serviceMetric.getSum() + services.stream().mapToInt(ServiceDTO::getPrice).sum());
        serviceMetric.setCount(serviceMetric.getCount() + services.size());
    }

}

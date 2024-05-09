package ru.anykeyers.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.statistics.ServicesRepository;
import ru.anykeyers.statistics.domain.ServiceMetric;
import ru.anykeyers.statistics.domain.StatisticsDTO;
import ru.anykeyers.statistics.processor.ServiceBatchProcessor;

/**
 * Реализация сервиса статистики
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ServiceBatchProcessor serviceBatchProcessor;

    private final ServicesRepository servicesRepository;

    @Override
    public StatisticsDTO getStatistics(Long carWashId) {
        serviceBatchProcessor.forceUpdate();
        ServiceMetric serviceMetric = servicesRepository.findByCarWashId(carWashId);
        return new StatisticsDTO(serviceMetric.getCount(), serviceMetric.getSum());
    }

    @Override
    public void processOrder(OrderDTO orderDTO) {
        log.info("Processing order {}", orderDTO);
        serviceBatchProcessor.addServicesMetric(orderDTO.getCarWashId(), orderDTO.getServices());
    }

}

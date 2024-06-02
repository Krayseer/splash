package ru.anykeyers.serviceofservices.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.serviceofservices.domain.ServiceMapper;
import ru.anykeyers.serviceofservices.ServiceRepository;
import ru.anykeyers.serviceofservices.domain.ServiceEntity;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

import java.util.List;

/**
 * Реализация сервиса обработки услуг
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceProcessorImpl implements ServiceProcessor {

    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        return serviceRepository.findByIdIn(serviceIds).stream().map(ServiceMapper::createDTO).toList();
    }

    @Override
    public long getServicesDuration(List<Long> serviceIds) {
        List<ServiceEntity> services = serviceRepository.findByIdIn(serviceIds);
        return services.stream().mapToLong(ServiceEntity::getDuration).sum();
    }

    @Override
    public List<ServiceDTO> getAllServices(Long carWashId) {
        return serviceRepository.findByCarWashId(carWashId).stream().map(ServiceMapper::createDTO).toList();
    }

    @Override
    public void saveService(Long carWashId, ServiceRequest serviceRequest) {
        ServiceEntity service = ServiceMapper.createService(carWashId, serviceRequest);
        serviceRepository.save(service);
        log.info("Saving service: {}", service);
    }

    @Override
    public void updateService(Long serviceId, ServiceRequest serviceRequest) {
        ServiceEntity service = serviceRepository.findById(serviceId).orElseThrow(
                () -> new RuntimeException("Service not found")
        );
        service.setDuration(serviceRequest.getDuration());
        service.setName(serviceRequest.getName());
        service.setPrice(serviceRequest.getPrice());
        serviceRepository.save(service);
        log.info("Updating service: {}", service);
    }

    @Override
    public void deleteService(Long serviceId) {
        serviceRepository.deleteById(serviceId);
        log.info("Deleting service: {}", serviceId);
    }

}

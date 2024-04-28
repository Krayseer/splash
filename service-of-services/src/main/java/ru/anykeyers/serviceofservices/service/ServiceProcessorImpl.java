package ru.anykeyers.serviceofservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.serviceofservices.ServiceFactory;
import ru.anykeyers.serviceofservices.ServiceRepository;
import ru.anykeyers.serviceofservices.domain.ServiceEntity;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.dto.ServiceDTO;

import java.util.List;

/**
 * Реализация сервиса обработки услуг
 */
@Service
@RequiredArgsConstructor
public class ServiceProcessorImpl implements ServiceProcessor {

    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceDTO> getAllServices(Long carWashId) {
        return serviceRepository.findByCarWashId(carWashId).stream()
                .map(ServiceFactory::createResponse)
                .toList();
    }

    @Override
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        return serviceRepository.findByIdIn(serviceIds).stream()
                .map(ServiceFactory::createResponse)
                .toList();
    }

    @Override
    public void saveService(Long carWashId, ServiceRequest serviceRequest) {
        ServiceEntity service = ServiceFactory.createService(carWashId, serviceRequest);
        serviceRepository.save(service);
    }

}

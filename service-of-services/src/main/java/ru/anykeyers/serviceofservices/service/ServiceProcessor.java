package ru.anykeyers.serviceofservices.service;

import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.dto.ServiceDTO;

import java.util.List;

/**
 * Сервис обработки услуг
 */
public interface ServiceProcessor {

    /**
     * Получить список всех услуг
     */
    List<ServiceDTO> getAllServices(Long carWashId);

    /**
     * Получить список услуг
     *
     * @param serviceIds идентификаторы услуг
     */
    List<ServiceDTO> getServices(List<Long> serviceIds);

    /**
     * Сохранить услугу
     *
     * @param serviceRequest даннные об услуге
     */
    void saveService(Long carWashId, ServiceRequest serviceRequest);
}

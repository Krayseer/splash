package ru.anykeyers.serviceofservices.processor;

import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

import java.util.List;

/**
 * Сервис обработки услуг
 */
public interface ServiceProcessor {

    /**
     * Получить список услуг
     *
     * @param serviceIds идентификаторы услуг
     */
    List<ServiceDTO> getServices(List<Long> serviceIds);

    /**
     * Получить длительность выполнения заказов
     *
     * @param serviceIds идентификаторы услуг
     */
    long getServicesDuration(List<Long> serviceIds);

    /**
     * Получить список всех услуг
     */
    List<ServiceDTO> getAllServices(Long carWashId);

    /**
     * Сохранить услугу
     *
     * @param serviceRequest даннные об услуге
     */
    void saveService(Long carWashId, ServiceRequest serviceRequest);
}

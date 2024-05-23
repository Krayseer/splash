package ru.anykeyers.serviceofservices.domain;

import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

/**
 * Фабрика по созданию услуг
 */
public final class ServiceMapper {

    /**
     * Создать услугу на основании данных в запросе
     *
     * @param serviceRequest данные для создания услуги
     */
    public static ServiceEntity createService(Long carWashId, ServiceRequest serviceRequest) {
        return ServiceEntity.builder()
                .carWashId(carWashId)
                .name(serviceRequest.getName())
                .duration(serviceRequest.getDuration())
                .price(serviceRequest.getPrice())
                .build();
    }

    /**
     * Создать DTO для передачи данных об услуге
     *
     * @param serviceEntity услуга
     */
    public static ServiceDTO createDTO(ServiceEntity serviceEntity) {
        return ServiceDTO.builder()
                .id(serviceEntity.getId())
                .name(serviceEntity.getName())
                .duration(DateUtils.toDate(serviceEntity.getDuration()))
                .price(serviceEntity.getPrice())
                .build();
    }

}

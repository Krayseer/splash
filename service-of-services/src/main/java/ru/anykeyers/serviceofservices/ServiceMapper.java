package ru.anykeyers.serviceofservices;

import ru.anykeyers.serviceofservices.domain.ServiceEntity;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                .duration(formatDate(serviceEntity.getDuration()))
                .price(serviceEntity.getPrice())
                .build();
    }

    private static String formatDate(long duration) {
        Date date = new Date(duration);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

}

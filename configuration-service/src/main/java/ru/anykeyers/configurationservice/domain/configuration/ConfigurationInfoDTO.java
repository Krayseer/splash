package ru.anykeyers.configurationservice.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.commonsapi.domain.dto.configuration.BoxDTO;

import java.util.List;

/**
 * Информационное DTO о конфигурации автомойки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationInfoDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Название
     */
    private String name;

    /**
     * Описание
     */
    private String description;

    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * Адрес
     */
    private String address;

    /**
     * Долгота
     */
    private String longitude;

    /**
     * Ширина
     */
    private String latitude;

    /**
     * Список услуг
     */
    private List<ServiceDTO> services;

    /**
     * Список боксов
     */
    private List<BoxDTO> boxes;

    /**
     * Список идентификаторов картинок
     */
    private List<String> photoUrls;

}

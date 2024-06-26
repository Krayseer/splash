package ru.anykeyers.configurationservice.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Запрос с данными об автомойке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationUpdateRequest {

    /**
     * Название автомойки
     */
    private String name;

    /**
     * Описание автомойки
     */
    private String description;

    /**
     * Телефонный номер
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
     * Широта
     */
    private String latitude;

    /**
     * Время открытия
     */
    private String openTime;

    /**
     * Время закрытия
     */
    private String closeTime;

    /**
     * Список фотографий
     */
    private List<MultipartFile> photos;

    /**
     * Включен ли режим обработки заказов менеджерами
     */
    private boolean managementProcessOrders;

    /**
     * Включен режим самообслуживания
     */
    private boolean selfService;

}

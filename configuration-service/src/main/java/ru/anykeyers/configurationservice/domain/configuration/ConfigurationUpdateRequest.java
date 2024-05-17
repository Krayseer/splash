package ru.anykeyers.configurationservice.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

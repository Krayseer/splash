package ru.anykeyers.configurationservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.configurationservice.domain.TypeOrganization;

/**
 * Запрос с данными об автомойке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRequest {

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

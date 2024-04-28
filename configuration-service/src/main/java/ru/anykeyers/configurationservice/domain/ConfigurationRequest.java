package ru.anykeyers.configurationservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос с данными для регистрации автомойки
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRequest {

    /**
     * ИНН
     */
    private String tin;

    /**
     * Тип организации
     */
    private String typeOrg;

    /**
     * Почта
     */
    private String email;

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

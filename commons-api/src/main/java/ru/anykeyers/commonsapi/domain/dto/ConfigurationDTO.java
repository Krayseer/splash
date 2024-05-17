package ru.anykeyers.commonsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Данные о конфигурации автомойки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDTO {

    /**
     * Идентификатор автомойки
     */
    private Long id;

    /**
     * Имя пользователя хозяина автомойки
     */
    private String username;

    /**
     * ИНН
     */
    private String tin;

    /**
     * Тип организации
     */
    private String typeOrganization;

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

    /**
     * Список услуг
     */
    private List<ServiceDTO> services;

    /**
     * Список боксов
     */
    private List<BoxDTO> boxes;

    /**
     * Время регистрации автомойки
     */
    private String createdAt;

}

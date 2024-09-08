package ru.anykeyers.commonsapi.domain.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Информация об организации
 */
@Getter
@Setter
@Builder
public class OrganizationInfo {
    /**
     * ИНН
     */
    private String tin;
    /**
     * Тип организации
     */
    private TypeOrganization typeOrganization;
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
     * Почта
     */
    private String email;
}

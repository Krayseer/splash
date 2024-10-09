package ru.anykeyers.configurationservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.configuration.TypeOrganization;

/**
 * Данные для регистрации автомойки
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationRegisterRequest {

    /**
     * ИНН
     */
    private String tin;

    /**
     * Тип организации
     */
    private TypeOrganization typeOrganization;

    /**
     * Почта
     */
    private String email;

}

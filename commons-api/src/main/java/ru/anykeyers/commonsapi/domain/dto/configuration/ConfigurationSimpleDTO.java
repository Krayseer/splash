package ru.anykeyers.commonsapi.domain.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Простой DTO с информацией о конфигурации автомойки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationSimpleDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Название
     */
    private String name;

}

package ru.anykeyers.commonsapi.domain.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о боксе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Название
     */
    private String name;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

}

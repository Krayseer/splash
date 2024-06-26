package ru.anykeyers.configurationservice.domain.box;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос с данными о боксе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxRequest {

    /**
     * Название
     */
    private String name;

}

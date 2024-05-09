package ru.anykeyers.configurationservice.domain.dto;

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

    private String name;

}

package ru.anykeyers.commonsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Данные об услуге для передачи
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Навзание
     */
    private String name;

    /**
     * Время выполнения
     */
    private long duration;

    /**
     * Цена выполнения
     */
    private int price;

}

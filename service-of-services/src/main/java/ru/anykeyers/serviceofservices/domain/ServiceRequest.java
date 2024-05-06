package ru.anykeyers.serviceofservices.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос с данными об услуге
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {

    /**
     * Название услуги
     */
    private String name;

    /**
     * Время выполнения услуги
     */
    private long duration;

    /**
     * Цена выполнения услуги
     */
    private int price;

}

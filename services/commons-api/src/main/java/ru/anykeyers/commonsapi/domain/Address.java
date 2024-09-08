package ru.anykeyers.commonsapi.domain;

import lombok.Data;

/**
 * Адрес
 */
@Data
public class Address {
    /**
     * Долгота
     */
    String longitude;
    /**
     * Широта
     */
    String latitude;
    /**
     * Название улицы
     */
    String address;
}

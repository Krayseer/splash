package ru.anykeyers.commonsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными об ошибке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T> {

    /**
     * Данные об ошибке
     */
    private T errorData;

}





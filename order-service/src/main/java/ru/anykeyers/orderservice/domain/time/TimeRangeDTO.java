package ru.anykeyers.orderservice.domain.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO с данными о временном отрезке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDTO {

    /**
     * Время начала
     */
    private String startTime;

    /**
     * Время конца
     */
    private String endTime;

}

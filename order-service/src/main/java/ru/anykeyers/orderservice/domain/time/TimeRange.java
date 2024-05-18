package ru.anykeyers.orderservice.domain.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Отрезок времени
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

    /**
     * Время начала
     */
    private Instant start;

    /**
     * Время конца
     */
    private Instant end;

    /**
     * Совпадает ли текущий отрезок с принимаемым
     */
    public boolean isInRange(Instant instant) {
        return !instant.isBefore(start) && instant.isBefore(end);
    }

}

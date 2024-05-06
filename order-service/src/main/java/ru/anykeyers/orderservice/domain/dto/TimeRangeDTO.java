package ru.anykeyers.orderservice.domain.dto;

import lombok.Data;
import ru.anykeyers.orderservice.domain.TimeRange;

/**
 * DTO с данными об отрезке времени
 */
@Data
public class TimeRangeDTO {

    /**
     * Время начала
     */
    private String startTime;

    /**
     * Время коцна
     */
    private String endTime;

    public TimeRangeDTO(TimeRange timeRange) {
        this.startTime = timeRange.getStart().toString();
        this.endTime = timeRange.getEnd().toString();
    }

}

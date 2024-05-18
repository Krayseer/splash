package ru.anykeyers.orderservice.domain.time;

/**
 * Маппер для отрезка времени
 */
public final class TimeRangeMapper {

    /**
     * Создать DTO для отрезка времени
     *
     * @param timeRange отрезок времени
     */
    public static TimeRangeDTO toDTO(TimeRange timeRange) {
        return new TimeRangeDTO(timeRange.getStart().toString(), timeRange.getEnd().toString());
    }

}

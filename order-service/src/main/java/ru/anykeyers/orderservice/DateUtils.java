package ru.anykeyers.orderservice;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для обработки времени
 */
public final class DateUtils {

    /**
     * Форматировать дату вида "dd-MM-yyyy" в {@link Instant}
     *
     * @param date дата в строковом формате
     */
    public static Instant formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    /**
     * Добавить время вида "часы:минуты" к {@link Instant времени}
     *
     * @param instant   дата
     * @param time      время, которое нужно добавить
     */
    public static Instant addTimeToInstant(Instant instant, String time) {
        LocalTime localTime = LocalTime.parse(time);
        Duration duration = Duration.ofHours(localTime.getHour()).plusMinutes(localTime.getMinute());
        return instant.plus(duration);
    }

}

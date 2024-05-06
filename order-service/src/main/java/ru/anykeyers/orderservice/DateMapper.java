package ru.anykeyers.orderservice;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Маппер дат
 */
public final class DateMapper {

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

}

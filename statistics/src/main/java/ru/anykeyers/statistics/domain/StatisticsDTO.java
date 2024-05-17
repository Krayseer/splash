package ru.anykeyers.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о статистике
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {

    /**
     * Количество оказанных услуг
     */
    private long servicesCount;

    /**
     * Сумма выполненных услуг
     */
    private long totalSummary;

}

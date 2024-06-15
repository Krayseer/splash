package ru.anykeyers.statistics.domain.metric;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Метрики по услугам
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SERVICE_METRIC")
public class ServiceMetric extends CarWashMetric {

    /**
     * Кол-во услуг
     */
    private long count;

    /**
     * Общая сумма выполненных услуг
     */
    private long sum;

}

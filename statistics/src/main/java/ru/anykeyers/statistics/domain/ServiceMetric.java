package ru.anykeyers.statistics.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ServiceMetric {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

    /**
     * Кол-во услуг
     */
    private long count;

    /**
     * Общая сумма выполненных услуг
     */
    private long sum;

}

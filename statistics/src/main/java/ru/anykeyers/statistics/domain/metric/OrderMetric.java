package ru.anykeyers.statistics.domain.metric;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_METRIC")
public class OrderMetric extends CarWashMetric {

    private Long count;

}

package ru.anykeyers.businessorderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Бизнес заказ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOrder {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор заказа
     */
    private Long orderId;

    /**
     * Идентификатор работника
     */
    private Long employeeId;

    public BusinessOrder(Long orderId, Long employeeId) {
        this.orderId = orderId;
        this.employeeId = employeeId;
    }

}

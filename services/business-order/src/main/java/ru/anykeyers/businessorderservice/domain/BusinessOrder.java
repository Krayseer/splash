package ru.anykeyers.businessorderservice.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Бизнес заказ
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOrder {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор заказа
     */
    @Column(name = "ORDER_ID")
    private Long orderId;

    /**
     * Имя пользователя работника
     */
    @Column(name = "EMPLOYEE_USERNAME")
    private String employeeUsername;

}

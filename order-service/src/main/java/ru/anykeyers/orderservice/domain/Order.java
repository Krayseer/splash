package ru.anykeyers.orderservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Заказ
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERS")
public class Order {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя заказчика
     */
    private String username;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

    /**
     * Идентификатор бокса
     */
    private Long boxId;

    /**
     * Статус заказа
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * Список идентификаторов услуг
     */
    @ElementCollection
    private List<Long> serviceIds;

    /**
     * Время заказа
     */
    private LocalDateTime time;

    /**
     * Тип оплаты
     */
    @Enumerated(EnumType.STRING)
    private PaymentType typePayment;

    /**
     * Время создания заказа
     */
    private LocalDateTime createdAt;

}

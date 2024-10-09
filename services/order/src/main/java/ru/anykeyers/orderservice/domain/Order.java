package ru.anykeyers.orderservice.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.commonsapi.domain.order.OrderState;
import ru.anykeyers.commonsapi.domain.PaymentType;

import java.time.Instant;
import java.util.List;

/**
 * Заказ
 */
@Getter
@Setter
@Entity
@Builder
@ToString
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
    private OrderState state;

    /**
     * Список идентификаторов услуг
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> serviceIds;

    /**
     * Время начала заказа
     */
    private long startTime;

    /**
     * Время окончания заказа
     */
    private long endTime;

    /**
     * Тип оплаты
     */
    @Enumerated(EnumType.STRING)
    private PaymentType typePayment;

    /**
     * Время создания заказа
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

}

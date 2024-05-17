package ru.anykeyers.orderservice.domain.order;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.commonsapi.domain.OrderState;
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
    private OrderState status;

    /**
     * Список идентификаторов услуг
     */
    @ElementCollection
    private List<Long> serviceIds;

    /**
     * Время начала заказа
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Instant startTime;

    /**
     * Время окончания заказа
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Instant endTime;

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

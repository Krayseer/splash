package ru.anykeyers.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Данные о заказе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

    /**
     * Идентификатор мойки
     */
    private Long boxId;

    /**
     * Идентификаторы услуг
     */
    private List<Long> serviceIds;

    /**
     * Время заказа
     */
    private LocalDateTime time;

    /**
     * Тип оплаты
     */
    private PaymentType typePayment;

}

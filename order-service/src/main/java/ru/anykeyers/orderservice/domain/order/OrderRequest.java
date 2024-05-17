package ru.anykeyers.orderservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.PaymentType;

import java.time.Instant;
import java.util.List;

/**
 * Данные о заказе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

    /**
     * Идентификаторы услуг
     */
    private List<Long> serviceIds;

    /**
     * Время заказа
     */
    private Instant time;

    /**
     * Тип оплаты
     */
    private PaymentType typePayment;

}

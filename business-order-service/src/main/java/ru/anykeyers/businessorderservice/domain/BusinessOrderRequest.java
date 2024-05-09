package ru.anykeyers.businessorderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о бизнес заказе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOrderRequest {

    /**
     * Идентификатор заказа
     */
    private Long orderId;

    /**
     * Идентификатор пользователя
     */
    private Long employeeId;

}

package ru.anykeyers.businessorderservice.domain;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long orderId;
    /**
     * Имя пользователя работника
     */
    @NotNull
    private String employeeUsername;

}

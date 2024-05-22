package ru.anykeyers.orderservice.domain.order;

import lombok.*;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

import java.util.List;

/**
 * Полный DTO заказа с данными об услугах
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullOrderDTO {

    /**
     * Данные о заказе
     */
    private OrderDTO orderDTO;

    /**
     * Данные об услугах
     */
    private List<ServiceDTO> services;

}

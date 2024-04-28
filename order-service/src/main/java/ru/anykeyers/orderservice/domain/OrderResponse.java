package ru.anykeyers.orderservice.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.dto.ServiceDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ответ с данными о заказе
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя пользователя
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
     * Статус
     */
    private OrderStatus status;

    /**
     * Список услуг
     */
    private List<ServiceDTO> services;

    /**
     * Время заказа
     */
    private LocalDateTime time;

    /**
     * Тип оплаты
     */
    private PaymentType typePayment;

    /**
     * Время создания
     */
    private LocalDateTime createdAt;

}

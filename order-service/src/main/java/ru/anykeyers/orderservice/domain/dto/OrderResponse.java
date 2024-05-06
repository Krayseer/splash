package ru.anykeyers.orderservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.dto.ServiceDTO;
import ru.anykeyers.orderservice.domain.constant.PaymentType;
import ru.anykeyers.orderservice.domain.constant.State;

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
    private State status;

    /**
     * Список услуг
     */
    private List<ServiceDTO> services;

    /**
     * Время начала заказа
     */
    private String startTime;

    /**
     * Время окончания заказа
     */
    private String endTime;

    /**
     * Тип оплаты
     */
    private PaymentType typePayment;

    /**
     * Время создания
     */
    private String createdAt;

}

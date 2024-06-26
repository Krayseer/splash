package ru.anykeyers.commonsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Ответ с данными о заказе
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

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
    private String status;

    /**
     * Список услуг
     */
    private List<Long> serviceIds;

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
    private String typePayment;

    /**
     * Время создания
     */
    private String createdAt;

}

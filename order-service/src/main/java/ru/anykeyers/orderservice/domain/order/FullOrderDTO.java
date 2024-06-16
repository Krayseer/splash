package ru.anykeyers.orderservice.domain.order;

import lombok.*;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;

import java.util.List;

/**
 * Полный DTO заказа с данными об услугах
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullOrderDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя пользователя
     */
    private UserDTO user;

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
     *  Данные об услугах
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
    private String typePayment;

    /**
     * Время создания
     */
    private String createdAt;

}

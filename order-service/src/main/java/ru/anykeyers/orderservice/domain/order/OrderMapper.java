package ru.anykeyers.orderservice.domain.order;

import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

import java.time.Instant;

/**
 * Фабрика создания заказов
 */
public final class OrderMapper {

    /**
     * Создать заказ
     *
     * @param username      имя пользователя создателя заказа
     * @param orderRequest  данные для создания заказа
     */
    public static Order createOrder(String username, OrderRequest orderRequest) {
        return Order.builder()
                .username(username)
                .carWashId(orderRequest.getCarWashId())
                .serviceIds(orderRequest.getServiceIds())
                .startTime(Instant.parse(orderRequest.getTime()))
                .typePayment(orderRequest.getTypePayment())
                .status(OrderState.WAIT_CONFIRM)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать данные для отправки о заказе
     *
     * @param order заказ
     */
    public static OrderDTO createDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .username(order.getUsername())
                .carWashId(order.getCarWashId())
                .boxId(order.getBoxId())
                .status(order.getStatus().name())
                .startTime(order.getStartTime().toString())
                .serviceIds(order.getServiceIds())
                .endTime(order.getEndTime().toString())
                .typePayment(order.getTypePayment().name())
                .createdAt(order.getCreatedAt().toString())
                .build();
    }

}

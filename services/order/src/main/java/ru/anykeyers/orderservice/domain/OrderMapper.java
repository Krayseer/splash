package ru.anykeyers.orderservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.Interval;
import ru.anykeyers.commonsapi.domain.order.OrderState;
import ru.anykeyers.commonsapi.domain.order.OrderDTO;
import ru.anykeyers.commonsapi.remote.RemoteConfigurationService;
import ru.anykeyers.commonsapi.remote.RemoteServicesService;
import ru.anykeyers.commonsapi.remote.RemoteUserService;

import java.time.Instant;
import java.util.List;

/**
 * Фабрика создания заказов
 */
@Component
@RequiredArgsConstructor
public final class OrderMapper {

    private final RemoteUserService remoteUserService;

    private final RemoteServicesService remoteServicesService;

    private final RemoteConfigurationService remoteConfigurationService;

    /**
     * Создать заказ
     *
     * @param username      имя пользователя создателя заказа
     * @param orderRequest  данные для создания заказа
     */
    public static Order toOrder(String username, OrderRequest orderRequest) {
        return Order.builder()
                .username(username)
                .carWashId(orderRequest.getCarWashId())
                .serviceIds(orderRequest.getServiceIds())
                .startTime(orderRequest.getTime())
                .typePayment(orderRequest.getTypePayment())
                .state(OrderState.WAIT_CONFIRM)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать данные для отправки о заказе
     *
     * @param order заказ
     */
    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .username(order.getUsername())
                .carWashId(order.getCarWashId())
                .boxId(order.getBoxId())
                .state(order.getState())
                .serviceIds(order.getServiceIds())
                .startTime(order.getStartTime())
                .endTime(order.getEndTime())
                .paymentType(order.getTypePayment())
                .createdAt(order.getCreatedAt())
                .build();
    }

    /**
     * Создать данные для отправки о заказах
     *
     * @param orders список заказов
     */
    public List<OrderDTO> toDTO(List<Order> orders) {
        return orders.stream().map(this::toDTO).toList();
    }

}

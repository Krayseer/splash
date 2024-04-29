package ru.anykeyers.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.dto.ServiceDTO;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.OrderRequest;
import ru.anykeyers.orderservice.domain.OrderResponse;
import ru.anykeyers.orderservice.domain.OrderStatus;
import ru.anykeyers.orderservice.service.remote.RemoteServicesService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Фабрика создания заказов
 */
@Component
@RequiredArgsConstructor
public final class OrderFactory {

    private final RemoteServicesService remoteServicesService;

    /**
     * Создать заказ
     *
     * @param username  имя пользователя создателя заказа
     * @param orderRequest  данные для создания заказа
     */
    public Order createOrder(String username, OrderRequest orderRequest) {
        return Order.builder()
                .username(username)
                .carWashId(orderRequest.getCarWashId())
                .boxId(1L)
                .status(OrderStatus.CREATED)
                .serviceIds(orderRequest.getServiceIds())
//                .time(orderRequest.getTime())
                .typePayment(orderRequest.getTypePayment())
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Создать данные для отправки данных о заказе
     *
     * @param order заказ
     */
    public OrderResponse createOrderResponse(Order order) {
        List<ServiceDTO> services = remoteServicesService.getServices(order.getServiceIds());
        return OrderResponse.builder()
                .id(order.getId())
                .username(order.getUsername())
                .carWashId(order.getCarWashId())
                .boxId(order.getBoxId())
                .status(order.getStatus())
//                .time(order.getTime())
                .services(services)
                .typePayment(order.getTypePayment())
//                .createdAt(order.getCreatedAt())
                .build();
    }

}

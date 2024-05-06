package ru.anykeyers.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.dto.ServiceDTO;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.dto.OrderRequest;
import ru.anykeyers.orderservice.domain.dto.OrderResponse;
import ru.anykeyers.orderservice.service.remote.RemoteServicesService;

import java.time.Instant;
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
     * @param username      имя пользователя создателя заказа
     * @param orderRequest  данные для создания заказа
     */
    public Order createOrder(String username, OrderRequest orderRequest) {
        return Order.builder()
                .username(username)
                .carWashId(orderRequest.getCarWashId())
                .serviceIds(orderRequest.getServiceIds())
                .startTime(orderRequest.getTime())
                .typePayment(orderRequest.getTypePayment())
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать список данных для отправки о заказах
     *
     * @param orders список заказов
     */
    public List<OrderResponse> createOrderResponse(List<Order> orders) {
        return orders.stream().map(this::createOrderResponse).toList();
    }

    /**
     * Создать данные для отправки о заказе
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
                .startTime(order.getStartTime().toString())
                .endTime(order.getEndTime().toString())
                .services(services)
                .typePayment(order.getTypePayment())
                .createdAt(order.getCreatedAt().toString())
                .build();
    }

}

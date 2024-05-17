package ru.anykeyers.orderservice.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.service.RemoteServicesService;

import java.time.Instant;
import java.util.List;

/**
 * Фабрика создания заказов
 */
@Component
@RequiredArgsConstructor
public final class OrderMapper {

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
                .status(OrderState.WAIT_CONFIRM)
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать список данных для отправки о заказах
     *
     * @param orders список заказов
     */
    public List<OrderDTO> createDTO(List<Order> orders) {
        return orders.stream().map(this::createDTO).toList();
    }

    /**
     * Создать данные для отправки о заказе
     *
     * @param order заказ
     */
    public OrderDTO createDTO(Order order) {
        List<ServiceDTO> services = remoteServicesService.getServices(order.getServiceIds());
        return OrderDTO.builder()
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

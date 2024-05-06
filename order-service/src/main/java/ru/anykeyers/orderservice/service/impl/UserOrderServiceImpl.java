package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderFactory;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.*;
import ru.anykeyers.orderservice.domain.constant.State;
import ru.anykeyers.orderservice.domain.dto.OrderRequest;
import ru.anykeyers.orderservice.domain.dto.OrderResponse;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.EmailService;
import ru.anykeyers.orderservice.service.StateService;
import ru.anykeyers.orderservice.service.UserOrderService;
import ru.anykeyers.orderservice.service.remote.RemoteServicesService;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final EmailService emailService;

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;

    private final StateService stateService;

    private final BoxService boxService;

    private final RemoteServicesService remoteServicesService;

    @Override
    public List<OrderResponse> getActiveOrders(String username) {
        List<Order> orders = orderRepository.findByUsernameAndStatus(username, State.WAIT_PROCESS);
        return orderFactory.createOrderResponse(orders);
    }

    @Override
    public List<OrderResponse> getProcessedOrders(String username) {
        List<Order> orders = orderRepository.findByUsernameAndStatus(username, State.PROCESSED);
        return orderFactory.createOrderResponse(orders);
    }

    @Override
    public OrderResponse saveOrder(String username, OrderRequest orderRequest) {
        long duration = remoteServicesService.getServicesDuration(orderRequest.getServiceIds());
        if (orderRequest.getTime() == null) {
            orderRequest.setTime(Instant.now());
        }
        Instant endTime = orderRequest.getTime().plusMillis(duration);
        Long boxId = boxService.getBoxForOrder(
                orderRequest.getCarWashId(), new TimeRange(orderRequest.getTime(), endTime)
        );
        if (boxId == null) {
            throw new RuntimeException("Not success save order, not found boxes");
        }
        Order order = orderFactory.createOrder(username, orderRequest);
        order.setStatus(stateService.nextState(order));
        order.setEndTime(endTime);
        order.setBoxId(boxId);
        emailService.sendEmail(order);
        return orderFactory.createOrderResponse(orderRepository.save(order));
    }

}

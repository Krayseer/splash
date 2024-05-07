package ru.anykeyers.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.orderservice.OrderFactory;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.*;
import ru.anykeyers.commonsapi.domain.State;
import ru.anykeyers.orderservice.domain.dto.OrderRequest;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.StateService;
import ru.anykeyers.orderservice.service.UserOrderService;
import ru.anykeyers.commonsapi.service.RemoteServicesService;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;

    private final StateService stateService;

    private final BoxService boxService;

    private final RemoteServicesService remoteServicesService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public List<OrderDTO> getActiveOrders(String username) {
        List<State> activeStates = List.of(State.WAIT_CONFIRM, State.WAIT_PROCESS, State.PROCESSING);
        List<Order> orders = orderRepository.findByUsernameAndStatusIn(username, activeStates);
        return orderFactory.createOrderResponse(orders);
    }

    @Override
    public List<OrderDTO> getProcessedOrders(String username) {
        List<Order> orders = orderRepository.findByUsernameAndStatus(username, State.PROCESSED);
        return orderFactory.createOrderResponse(orders);
    }

    @Override
    @SneakyThrows
    public OrderDTO saveOrder(String username, OrderRequest orderRequest) {
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
        OrderDTO orderDTO = orderFactory.createOrderResponse(orderRepository.save(order));
        kafkaTemplate.send(MessageQueue.ORDER_CREATE, objectMapper.writeValueAsString(orderDTO));
        return orderDTO;
    }

}

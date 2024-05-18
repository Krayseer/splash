package ru.anykeyers.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.orderservice.domain.TimeRange;
import ru.anykeyers.orderservice.domain.order.OrderMapper;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.order.Order;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.orderservice.exception.BoxFreeNotFoundException;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.OrderService;

import java.time.Instant;
import java.util.List;

/**
 * Реализация сервисов обработки заказов
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final BoxService boxService;

    private final OrderMapper orderMapper;

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    private final RemoteServicesService remoteServicesService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)
        );
        return orderMapper.createDTO(order);
    }

    @Override
    public void applyOrderEmployee(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        order.setStatus(OrderState.WAIT_PROCESS);
        orderRepository.save(order);
        log.info("Process order apply employee: {}", order);
    }

    @Override
    public int getActiveOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, OrderState.WAIT_PROCESS);
    }

    @Override
    public int getProcessingOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, OrderState.PROCESSING);
    }

    @Override
    public int getProcessedOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, OrderState.PROCESSED);
    }

    @Override
    public List<OrderDTO> getActiveOrders(String username) {
        List<OrderState> activeOrderStates = List.of(OrderState.WAIT_CONFIRM, OrderState.WAIT_PROCESS, OrderState.PROCESSING);
        List<Order> orders = orderRepository.findByUsernameAndStatusIn(username, activeOrderStates);
        return orderMapper.createDTO(orders);
    }

    @Override
    public List<OrderDTO> getProcessedOrders(String username) {
        List<Order> orders = orderRepository.findByUsernameAndStatus(username, OrderState.PROCESSED);
        return orderMapper.createDTO(orders);
    }

    @Override
    @SneakyThrows
    public OrderDTO saveOrder(String username, OrderRequest orderRequest) {
        log.info("Processing order: {}", orderRequest);
        long duration = remoteServicesService.getServicesDuration(orderRequest.getServiceIds());
        if (orderRequest.getTime() == null) {
            orderRequest.setTime(Instant.now());
        }
        Instant endTime = orderRequest.getTime().plusMillis(duration);
        Long boxId = boxService.getBoxForOrder(
                orderRequest.getCarWashId(), new TimeRange(orderRequest.getTime(), endTime)
        );
        if (boxId == null) {
            throw new BoxFreeNotFoundException();
        }
        Order order = orderMapper.createOrder(username, orderRequest);
        order.setEndTime(endTime);
        order.setBoxId(boxId);
        OrderDTO orderDTO = orderMapper.createDTO(orderRepository.save(order));
        kafkaTemplate.send(MessageQueue.ORDER_CREATE, objectMapper.writeValueAsString(orderDTO));
        return orderDTO;
    }

}

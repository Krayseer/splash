package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.user.User;
import ru.anykeyers.commonsapi.remote.RemoteUserService;
import ru.anykeyers.commonsapi.domain.order.OrderState;
import ru.anykeyers.commonsapi.remote.RemoteServicesService;
import ru.anykeyers.commonsapi.domain.Interval;
import ru.anykeyers.orderservice.domain.OrderMapper;
import ru.anykeyers.orderservice.repository.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.OrderRequest;
import ru.anykeyers.orderservice.domain.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.OrderService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Реализация сервисов обработки заказов
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final BoxService boxService;
    private final OrderRepository orderRepository;
    private final RemoteUserService remoteUserService;
    private final RemoteServicesService remoteServicesService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderMapper orderMapper;

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> getOrders(List<Long> orderIds) {
        return orderRepository.findAllById(orderIds);
    }

    @Override
    public void applyOrderEmployee(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        order.setState(OrderState.WAIT_PROCESS);
        orderRepository.save(order);
        log.info("Process order apply employee: {}", order);
    }

    @Override
    public void disappointEmployeeFromOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        order.setState(OrderState.WAIT_CONFIRM);
        orderRepository.save(order);
        log.info("Disappoint employee from order: {}", order);
    }

    @Override
    public List<Order> getOrders(Long carWashId, Instant date) {
        List<Order> orders = orderRepository.findByCarWashIdAndStateIn(
                carWashId, List.of(OrderState.WAIT_CONFIRM, OrderState.WAIT_PROCESS, OrderState.PROCESSING)
        );
        Instant endTime = date.plus(1, ChronoUnit.DAYS);
        return orders.stream()
                .filter(order -> order.getStartTime().equals(date) || (order.getStartTime().isAfter(date) && order.getEndTime().isBefore(endTime)))
                .toList();
    }

    @Override
    public List<Order> getWaitConfirmOrders(Long carWashId) {
        return orderRepository.findByCarWashIdAndState(carWashId, OrderState.WAIT_CONFIRM);
    }

    @Override
    public int getOrdersCount(Long carWashId, OrderState orderState) {
        return orderRepository.countByCarWashIdAndState(carWashId, orderState);
    }

    @Override
    public List<Order> getActiveOrders(User user) {
        List<OrderState> activeOrderStates = List.of(OrderState.WAIT_CONFIRM, OrderState.WAIT_PROCESS, OrderState.PROCESSING);
        return orderRepository.findByUsernameAndStateIn(user.getUsername(), activeOrderStates);
    }

    @Override
    public List<Order> getProcessedOrders(String username) {
        return orderRepository.findByUsernameAndState(username, OrderState.PROCESSED);
    }

    @Override
    @SneakyThrows
    public Order saveOrder(String username, OrderRequest orderRequest) {
        log.info("Processing order: {}", orderRequest);
        Order order = OrderMapper.toOrder(username, orderRequest);
        Instant endTime = calculateEndTime(orderRequest);
        Long boxId = boxService.getBoxForOrder(orderRequest.getCarWashId(), new Interval(orderRequest.getTime(), endTime));
        order.setEndTime(endTime);
        order.setBoxId(boxId);
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(MessageQueue.ORDER_CREATE, orderMapper.toDTO(savedOrder));
        return savedOrder;
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        log.info("Delete order: {}", orderId);
    }

    @Override
    public void deleteOrder(String username, Long orderId) {
        orderRepository.deleteByUsernameAndId(username, orderId);
        log.info("Delete order: {}", orderId);
    }

    /**
     * Рассчитать время окончания заказа
     */
    private Instant calculateEndTime(OrderRequest orderRequest) {
        long duration = remoteServicesService.getServicesDuration(orderRequest.getServiceIds());
        return orderRequest.getTime().plusMillis(duration);
    }

}

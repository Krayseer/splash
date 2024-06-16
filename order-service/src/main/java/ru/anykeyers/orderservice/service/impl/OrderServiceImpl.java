package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;
import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.orderservice.domain.time.TimeRange;
import ru.anykeyers.orderservice.domain.order.OrderMapper;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.order.Order;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.orderservice.exception.BoxFreeNotFoundException;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.EventService;
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

    private final EventService eventService;

    private final OrderRepository orderRepository;

    private final RemoteUserService remoteUserService;

    private final RemoteServicesService remoteServicesService;

    @Override
    public FullOrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)
        );
        return OrderMapper.toFullDTO(order, remoteUserService, remoteServicesService);
    }

    @Override
    public List<OrderDTO> getOrders(List<Long> orderIds) {
        List<Order> orders = orderRepository.findAllById(orderIds);
        return orders.stream().map(OrderMapper::toDTO).toList();
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
    public void disappointEmployeeFromOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        order.setStatus(OrderState.WAIT_CONFIRM);
        orderRepository.save(order);
        log.info("Disappoint employee from order: {}", order);
    }

    @Override
    public List<FullOrderDTO> getOrders(Long carWashId, Instant date) {
        List<Order> orders = orderRepository.findByCarWashIdAndStatusIn(
                carWashId, List.of(OrderState.WAIT_CONFIRM, OrderState.WAIT_PROCESS, OrderState.PROCESSING)
        );
        Instant endTime = date.plus(1, ChronoUnit.DAYS);
        return getFullOrderDTOList(
                orders.stream()
                .filter(order -> order.getStartTime().equals(date)
                        || (order.getStartTime().isAfter(date) && order.getEndTime().isBefore(endTime)
                ))
                .toList()
        );
    }

    @Override
    public List<FullOrderDTO> getWaitConfirmOrders(Long carWashId) {
        List<Order> orders = orderRepository.findByCarWashIdAndStatus(carWashId, OrderState.WAIT_CONFIRM);
        return getFullOrderDTOList(orders);
    }

    @Override
    public int getOrdersCount(Long carWashId, OrderState orderState) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, orderState);
    }
    @Override
    public List<FullOrderDTO> getActiveOrders(String username) {
        List<OrderState> activeOrderStates = List.of(OrderState.WAIT_CONFIRM, OrderState.WAIT_PROCESS, OrderState.PROCESSING);
        List<Order> orders = orderRepository.findByUsernameAndStatusIn(username, activeOrderStates);
        return getFullOrderDTOList(orders);
    }

    @Override
    public List<FullOrderDTO> getProcessedOrders(String username) {
        List<Order> orders = orderRepository.findByUsernameAndStatus(username, OrderState.PROCESSED);
        return getFullOrderDTOList(orders);
    }

    @Override
    @SneakyThrows
    public OrderDTO saveOrder(String username, OrderRequest orderRequest) {
        log.info("Processing order: {}", orderRequest);
        Order order = OrderMapper.toOrder(username, orderRequest);
        Instant endTime = calculateEndTime(Instant.parse(orderRequest.getTime()), orderRequest.getServiceIds());
        Long boxId = boxService
                .getBoxForOrder(orderRequest.getCarWashId(), new TimeRange(Instant.parse(orderRequest.getTime()), endTime))
                .orElseThrow(BoxFreeNotFoundException::new);
        order.setEndTime(endTime);
        order.setBoxId(boxId);
        Order savedOrder = orderRepository.save(order);
        OrderDTO orderDTO = OrderMapper.toDTO(savedOrder);
        eventService.sendOrderCreatedEvent(orderDTO);
        return orderDTO;
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
     *
     * @param startTime     время начала заказа
     * @param serviceIds    идентификаторы услуг
     */
    private Instant calculateEndTime(Instant startTime, List<Long> serviceIds) {
        long duration = remoteServicesService.getServicesDuration(serviceIds);
        return startTime.plusMillis(duration);
    }

    /**
     * Создать список данных для отправки о заказах
     *
     * @param orders список заказов
     */
    private List<FullOrderDTO> getFullOrderDTOList(List<Order> orders) {
        return orders.stream()
                .map(order -> OrderMapper.toFullDTO(order, remoteUserService, remoteServicesService))
                .toList();
    }

    /**
     * Создать список данных для отправки о заказах
     *
     * @param orders список заказов
     */
    private List<OrderDTO> getOrderDTOList(List<Order> orders) {
        return orders.stream().map(OrderMapper::toDTO).toList();
    }

}

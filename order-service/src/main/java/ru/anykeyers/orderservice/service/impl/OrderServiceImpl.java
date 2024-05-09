package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderFactory;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.OrderService;
import ru.anykeyers.orderservice.service.StateService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderFactory orderFactory;

    private final StateService stateService;

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)
        );
        return orderFactory.createOrderResponse(order);
    }

    @Override
    public void applyOrderEmployee(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        stateService.nextState(order);
        log.info("Process order apply employee: {}", order);
    }

}

package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderFactory;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(id)
        );
        return orderFactory.createOrderResponse(order);
    }

}

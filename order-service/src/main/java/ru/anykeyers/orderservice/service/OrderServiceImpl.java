package ru.anykeyers.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderFactory;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.OrderDTO;
import ru.anykeyers.orderservice.domain.OrderResponse;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.exception.UserAlreadyExistsOrderException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final EmailService emailService;

    private final OrderFactory orderFactory;

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse getOrder(String username) {
        Order order = orderRepository.findByUsername(username).orElseThrow(
                () -> new OrderNotFoundException(username)
        );
        return orderFactory.createOrderResponse(order);
    }

    @Override
    public void saveOrder(String username, OrderDTO orderDTO) {
        Optional<Order> order = orderRepository.findByUsername(username);
        if (order.isPresent()) {
            throw new UserAlreadyExistsOrderException(username);
        }
        orderRepository.save(orderFactory.createOrder(orderDTO));
        emailService.sendEmail(orderDTO);
    }

}

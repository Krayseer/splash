package ru.anykeyers.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BASE_NAME)
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

}

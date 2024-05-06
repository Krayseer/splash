package ru.anykeyers.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.orderservice.domain.constant.ControllerName;
import ru.anykeyers.orderservice.domain.dto.OrderRequest;
import ru.anykeyers.orderservice.domain.dto.OrderResponse;
import ru.anykeyers.orderservice.service.UserOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.USER_ORDER_NAME)
public class UserOrderController {

    private final UserOrderService orderService;

    @GetMapping("/active")
    public List<OrderResponse> getActiveOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getActiveOrders(jwt.getSubject());
    }

    @GetMapping("/processed")
    public List<OrderResponse> getProcessedOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getProcessedOrders(jwt.getSubject());
    }

    @PostMapping
    public OrderResponse saveOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequest orderRequest) {
        return orderService.saveOrder(jwt.getSubject(), orderRequest);
    }

}

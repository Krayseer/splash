package ru.anykeyers.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.service.UserOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.USER_ORDER_NAME)
public class UserOrderController {

    private final UserOrderService orderService;

    @GetMapping("/active")
    public List<OrderDTO> getActiveOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getActiveOrders(jwt.getSubject());
    }

    @GetMapping("/processed")
    public List<OrderDTO> getProcessedOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getProcessedOrders(jwt.getSubject());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public OrderDTO saveOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequest orderRequest) {
        return orderService.saveOrder(jwt.getSubject(), orderRequest);
    }

}

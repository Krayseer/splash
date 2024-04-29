package ru.anykeyers.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.orderservice.domain.OrderRequest;
import ru.anykeyers.orderservice.domain.OrderResponse;
import ru.anykeyers.orderservice.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public OrderResponse getOrder(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getOrder(jwt.getSubject());
    }

    @PostMapping
    public void saveOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(jwt.getSubject(), orderRequest);
    }

}

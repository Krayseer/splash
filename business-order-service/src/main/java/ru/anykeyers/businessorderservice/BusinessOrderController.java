package ru.anykeyers.businessorderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.businessorderservice.domain.BusinessOrder;
import ru.anykeyers.businessorderservice.domain.BusinessOrderRequest;
import ru.anykeyers.businessorderservice.service.OrderService;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business-order")
public class BusinessOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getOrders(jwt.getSubject());
    }

    @GetMapping("/{username}")
    public List<OrderDTO> getOrders(@PathVariable String username) {
        return orderService.getOrders(username);
    }

    @PostMapping
    public void saveOrder(@RequestBody BusinessOrderRequest request) {
        orderService.processOrder(request);
    }

}

package ru.anykeyers.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.order.OrderDTO;
import ru.anykeyers.orderservice.domain.OrderMapper;
import ru.anykeyers.orderservice.service.OrderService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BASE_CONTROLLER)
@Tag(name = "Обработка заказов")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Operation(summary = "Получить заказ по идентификатору")
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderMapper.toDTO(orderService.getOrder(id));
    }

    @Operation(summary = "Получить список заказов по идентификаторам")
    @GetMapping("/list")
    public List<OrderDTO> getOrders(@RequestParam Long[] orderIds) {
        return orderMapper.toDTO(orderService.getOrders(Arrays.stream(orderIds).toList()));
    }

    @GetMapping("/test")
    public String test(Principal user) {
        return user.toString();
    }

}

package ru.anykeyers.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.orderservice.service.OrderService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BASE_NAME)
@Tag(name = "Обработка заказов")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить заказ по идентификатору")
    @GetMapping("/{id}")
    public FullOrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @Operation(summary = "Получить список заказов по идентификаторам")
    @GetMapping("/list")
    public List<OrderDTO> getOrders(@RequestParam Long[] orderIds) {
        return orderService.getOrders(Arrays.stream(orderIds).toList());
    }

}

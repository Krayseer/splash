package ru.anykeyers.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Обработка заказов")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить заказ по идентификатору")
    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

}

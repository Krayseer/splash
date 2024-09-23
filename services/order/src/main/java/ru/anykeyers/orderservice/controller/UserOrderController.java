package ru.anykeyers.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.utils.JwtUtils;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.OrderMapper;
import ru.anykeyers.orderservice.domain.OrderRequest;
import ru.anykeyers.commonsapi.domain.order.OrderDTO;
import ru.anykeyers.orderservice.service.UserOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.USER_ORDER_CONTROLLER)
@Tag(name = "Обработка заказов пользователя")
public class UserOrderController {

    private final OrderMapper orderMapper;

    private final UserOrderService orderService;

    @Operation(summary = "Получить все активные заказы пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Получение списка заказов пользователя",
                    content = {
                            @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class))
                    })
    })
    @GetMapping("/active")
    public List<OrderDTO> getActiveOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderMapper.toDTO(orderService.getActiveOrders(JwtUtils.extractUser(jwt)));
    }

    @Operation(summary = "Получить все завершенные заказы пользователя")
    @GetMapping("/processed")
    public List<OrderDTO> getProcessedOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderMapper.toDTO(orderService.getProcessedOrders(jwt.getSubject()));
    }

    @Operation(summary = "Сохранить заказ пользователя")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public OrderDTO saveOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequest orderRequest) {
        Order savedOrder = orderService.saveOrder(jwt.getSubject(), orderRequest);
        return orderMapper.toDTO(savedOrder);
    }

    @Operation(summary = "Удалить заказ пользователя")
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@AuthenticationPrincipal Jwt jwt, @PathVariable Long orderId) {
        orderService.deleteOrder(jwt.getSubject(), orderId);
    }

}

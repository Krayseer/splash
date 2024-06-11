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
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.service.UserOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.USER_ORDER_NAME)
@Tag(name = "Обработка заказов пользователя")
public class UserOrderController {

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
    public List<FullOrderDTO> getActiveOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getActiveOrders(jwt.getSubject());
    }

    @Operation(summary = "Получить все завершенные заказы пользователя")
    @GetMapping("/processed")
    public List<FullOrderDTO> getProcessedOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getProcessedOrders(jwt.getSubject());
    }

    @Operation(summary = "Сохранить заказ пользователя")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public OrderDTO saveOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderRequest orderRequest) {
        return orderService.saveOrder(jwt.getSubject(), orderRequest);
    }

    @Operation(summary = "Удалить заказ пользователя")
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@AuthenticationPrincipal Jwt jwt, @PathVariable Long orderId) {
        orderService.deleteOrder(jwt.getSubject(), orderId);
    }

}

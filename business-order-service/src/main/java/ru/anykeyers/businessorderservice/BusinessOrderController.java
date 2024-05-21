package ru.anykeyers.businessorderservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.businessorderservice.domain.BusinessOrderRequest;
import ru.anykeyers.businessorderservice.domain.ControllerName;
import ru.anykeyers.businessorderservice.service.OrderService;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BASE_NAME)
@Tag(name = "Обработка бизнес заказов")
public class BusinessOrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить список заказов работника")
    @GetMapping("/employee")
    public List<OrderDTO> getOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getOrders(jwt.getSubject());
    }

    @Operation(summary = "Получить список заказов работника")
    @GetMapping("/{username}")
    public List<OrderDTO> getOrders(@PathVariable String username) {
        return orderService.getOrders(username);
    }

    @Operation(summary = "Назначить работника на заказ")
    @PostMapping("/appoint")
    public void appointOrderEmployee(@RequestBody BusinessOrderRequest request) {
        orderService.appointOrderEmployee(request);
    }

}

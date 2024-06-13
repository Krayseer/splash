package ru.anykeyers.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.domain.order.FullOrderDTO;
import ru.anykeyers.orderservice.domain.time.TimeRange;
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.orderservice.domain.time.TimeRangeDTO;
import ru.anykeyers.orderservice.domain.time.TimeRangeMapper;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.CarWashOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.CAR_WASH_ORDER_CONTROLLER)
@Tag(name = "Обработка заказов автомойки")
public class CarWashOrderController {

    private final BoxService boxService;

    private final CarWashOrderService orderService;

    @Operation(summary = "Получить список свободных отрезков времени в конкретный день")
    @GetMapping("/free-times")
    public List<TimeRangeDTO> getOrderFreeTimes(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("id") Long carWashId,
            @Parameter(description = "Дата для получения свободных отрезков") @RequestParam("date") String date
    ) {
        List<TimeRange> timeRanges = boxService.getOrderFreeTimes(carWashId, DateUtils.toInstant(date));
        return timeRanges.stream().map(TimeRangeMapper::toDTO).toList();
    }

    @Operation(summary = "Получить список заказов автомойки в конкретный день")
    @GetMapping("/by-date")
    public List<OrderDTO> getOrdersByDate(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("id") Long carWashId,
            @Parameter(description = "Дата для получения заказов") @RequestParam("date") String date
    ) {
        return orderService.getOrders(carWashId, DateUtils.toInstant(date));
    }

    @Operation(summary = "Получить количество активных заказов автомойки")
    @GetMapping("/active/count")
    public int getActiveOrdersCount(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("carWashId") Long carWashId
    ) {
        return orderService.getActiveOrdersCount(carWashId);
    }

    @Operation(summary = "Получить список заказов, ожидающих одобрения")
    @GetMapping("/wait-confirm")
    public List<FullOrderDTO> getWaitConfirmOrders(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("carWashId") Long carWashId
    ) {
        return orderService.getWaitConfirmOrders(carWashId);
    }

    @Operation(summary = "Получить количество заказов, ожидающих одобрения")
    @GetMapping("/wait-confirm/count")
    public int getWaitConfirmOrdersCount(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("carWashId") Long carWashId
    ) {
        return orderService.getWaitConfirmOrdersCount(carWashId);
    }

    @Operation(summary = "Получить количество заказов, находящихся в обработке")
    @GetMapping("/processing/count")
    public int getProcessingOrdersCount(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("carWashId") Long carWashId
    ) {
        return orderService.getProcessingOrdersCount(carWashId);
    }

    @Operation(summary = "Получить количество обработанных заказов")
    @GetMapping("/processed/count")
    public int getProcessedOrdersCount(
            @Parameter(description = "Идентификатор автомойки") @RequestParam("carWashId") Long carWashId
    ) {
        return orderService.getProcessedOrdersCount(carWashId);
    }

}

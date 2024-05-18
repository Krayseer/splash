package ru.anykeyers.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.DateUtils;
import ru.anykeyers.orderservice.domain.time.TimeRange;
import ru.anykeyers.orderservice.domain.ControllerName;
import ru.anykeyers.orderservice.domain.time.TimeRangeDTO;
import ru.anykeyers.orderservice.domain.time.TimeRangeMapper;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.orderservice.service.CarWashOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.CAR_WASH_ORDER_NAME)
public class CarWashOrderController {

    private final CarWashOrderService orderService;

    private final BoxService boxService;

    @GetMapping("/free-times")
    public List<TimeRangeDTO> getOrderFreeTimes(@RequestParam("id") Long carWashId, @RequestParam("date") String date) {
        List<TimeRange> timeRanges = boxService.getOrderFreeTimes(carWashId, DateUtils.formatDate(date));
        return timeRanges.stream().map(TimeRangeMapper::toDTO).toList();
    }

    @GetMapping("/active/count")
    public int getActiveOrdersCount(@RequestParam("carWashId") Long carWashId) {
        return orderService.getActiveOrdersCount(carWashId);
    }

    @GetMapping("/wait-confirm")
    public List<OrderDTO> getWaitConfirmOrders(@RequestParam("carWashId") Long carWashId) {
        return orderService.getWaitConfirmOrders(carWashId);
    }

    @GetMapping("/wait-confirm/count")
    public int getWaitConfirmOrdersCount(@RequestParam("carWashId") Long carWashId) {
        return orderService.getWaitConfirmOrdersCount(carWashId);
    }

    @GetMapping("/processing/count")
    public int getProcessingOrdersCount(@RequestParam("carWashId") Long carWashId) {
        return orderService.getProcessingOrdersCount(carWashId);
    }

    @GetMapping("/processed/count")
    public int getProcessedOrdersCount(@RequestParam("carWashId") Long carWashId) {
        return orderService.getProcessedOrdersCount(carWashId);
    }

}

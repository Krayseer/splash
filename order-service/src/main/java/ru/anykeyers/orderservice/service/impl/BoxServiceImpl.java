package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.TimeRange;
import ru.anykeyers.orderservice.domain.dto.TimeRangeDTO;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация сервиса орбаботки боксов
 */
@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {

    private final OrderRepository orderRepository;

    private final RemoteConfigurationService remoteConfigurationService;

    @Override
    public Long getBoxForOrder(Long carWashId, TimeRange timeRange) {
        List<Long> boxIds = remoteConfigurationService.getBoxIds(carWashId);
        Map<Long, List<TimeRange>> orderDurations = getOrdersByBoxId(boxIds, timeRange.getStart(), timeRange.getEnd());
        for(Map.Entry<Long, List<TimeRange>> entry : orderDurations.entrySet()) {
            boolean isInRange = false;
            for (TimeRange range : entry.getValue()) {
                if (range.isInRange(timeRange.getStart()) || range.isInRange(timeRange.getEnd())) {
                    isInRange = true;
                }
            }
            if (!isInRange) {
                return entry.getKey();
            }
        }
        return boxIds.getFirst();
    }

    @Override
    public List<TimeRangeDTO> getOrderFreeTimes(Long carWashId, Instant time) {
        List<Long> boxIds = remoteConfigurationService.getBoxIds(carWashId);
        Instant endTime = time.plus(1, ChronoUnit.DAYS);
        Map<Long, List<TimeRange>> orderDurations = getOrdersByBoxId(boxIds, time, endTime);
        List<TimeRange> timeRanges = new ArrayList<>();
        orderDurations.forEach((key, value) -> timeRanges.addAll(
                findFreeTimeRanges(value, time, time.plusSeconds(24*60*60))
        ));
        return timeRanges.stream().map(TimeRangeDTO::new).toList();
    }

    private Map<Long, List<TimeRange>> getOrdersByBoxId(List<Long> boxIds, Instant start, Instant end) {
        List<Order> orders = orderRepository.findByBoxIdIn(boxIds).stream()
                .filter(o -> o.getStartTime().isAfter(start))
                .filter(o -> o.getEndTime().isBefore(end))
                .toList();
        Map<Long, List<TimeRange>> ordersByBoxId = boxIds.stream()
                .collect(Collectors.toMap(
                        boxId -> boxId,
                        orderList -> new ArrayList<>()
                ));
        orders.forEach(order ->
                ordersByBoxId.get(order.getBoxId()).add(new TimeRange(order.getStartTime(), order.getEndTime())));
        return ordersByBoxId;
    }

    private List<TimeRange> findFreeTimeRanges(List<TimeRange> busyTimeRanges, Instant startDateTime, Instant endDateTime) {
        busyTimeRanges.sort(Comparator.comparing(TimeRange::getStart));
        List<TimeRange> freeTimeRanges = new ArrayList<>();
        if (busyTimeRanges.isEmpty()) {
            return Collections.singletonList(new TimeRange(startDateTime, endDateTime));
        }
        if (startDateTime.isBefore(busyTimeRanges.getFirst().getStart())) {
            freeTimeRanges.add(new TimeRange(startDateTime, busyTimeRanges.getFirst().getStart()));
        }
        for (int i = 0; i < busyTimeRanges.size() - 1; i++) {
            TimeRange current = busyTimeRanges.get(i);
            TimeRange next = busyTimeRanges.get(i + 1);
            if (!current.getEnd().equals(next.getStart())
                    && !current.getEnd().isAfter(next.getStart())
                    && !next.getStart().isAfter(endDateTime)) {
                freeTimeRanges.add(new TimeRange(current.getEnd(), next.getStart()));
            }
        }
        if (endDateTime.isAfter(busyTimeRanges.getLast().getEnd())) {
            freeTimeRanges.add(new TimeRange(busyTimeRanges.getLast().getEnd(), endDateTime));
        }
        return freeTimeRanges;
    }

}

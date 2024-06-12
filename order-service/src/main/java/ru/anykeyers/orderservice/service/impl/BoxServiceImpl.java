package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.configuration.BoxDTO;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.order.Order;
import ru.anykeyers.orderservice.domain.time.TimeRange;
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
    public Optional<Long> getBoxForOrder(Long carWashId, TimeRange timeRange) {
        List<Long> boxIds = remoteConfigurationService.getBoxIds(carWashId);
        Map<Long, List<TimeRange>> orderDurations = getOrdersByBoxId(boxIds);
        return orderDurations.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .noneMatch(range -> timeRange.isInRange(range.getStart()) || timeRange.isInRange(range.getEnd())))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public List<TimeRange> getOrderFreeTimes(Long carWashId, Instant time) {
        ConfigurationDTO configuration = remoteConfigurationService.getConfiguration(carWashId);
        List<Long> boxIds = configuration.getBoxes().stream().map(BoxDTO::getId).collect(Collectors.toList());
        Instant startTime = Optional.ofNullable(configuration.getOpenTime())
                .map(openTime -> DateUtils.addTimeToInstant(time, openTime))
                .orElse(time);
        Instant endTime = Optional.ofNullable(configuration.getCloseTime())
                .map(closeTime -> DateUtils.addTimeToInstant(time, closeTime))
                .orElse(time.plus(1, ChronoUnit.DAYS));
        return getOrdersByBoxIdFiltered(boxIds, startTime, endTime).values().stream()
                .flatMap(busyTimeRanges -> findFreeTimeRanges(busyTimeRanges, startTime, endTime).stream())
                .collect(Collectors.toList());
    }

    private Map<Long, List<TimeRange>> getOrdersByBoxId(List<Long> boxIds) {
        List<Order> orders = orderRepository.findByBoxIdIn(boxIds);
        return getOrdersByBoxId(boxIds, orders);
    }

    private Map<Long, List<TimeRange>> getOrdersByBoxIdFiltered(List<Long> boxIds, Instant start, Instant end) {
        List<Order> orders = orderRepository.findByBoxIdIn(boxIds).stream()
                .filter(o -> o.getStartTime().equals(start) || o.getStartTime().isAfter(start))
                .filter(o -> o.getEndTime().equals(end) || o.getEndTime().isBefore(end))
                .toList();
        return getOrdersByBoxId(boxIds, orders);
    }

    private Map<Long, List<TimeRange>> getOrdersByBoxId(List<Long> boxIds, List<Order> orders) {
        return boxIds.stream()
                .collect(Collectors.toMap(
                        boxId -> boxId,
                        boxId -> orders.stream()
                                .filter(order -> Objects.equals(order.getBoxId(), boxId))
                                .map(order -> new TimeRange(order.getStartTime(), order.getEndTime()))
                                .collect(Collectors.toList())));
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

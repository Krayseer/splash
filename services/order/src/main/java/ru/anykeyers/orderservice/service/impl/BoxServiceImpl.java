package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.commonsapi.domain.configuration.BoxDTO;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationDTO;
import ru.anykeyers.orderservice.domain.exception.BoxFreeNotFoundException;
import ru.anykeyers.orderservice.repository.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.commonsapi.domain.Interval;
import ru.anykeyers.orderservice.service.BoxService;
import ru.anykeyers.commonsapi.remote.RemoteConfigurationService;

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
    public Long getBoxForOrder(Long carWashId, Interval interval) {
        List<Long> boxIds = remoteConfigurationService.getBoxIds(carWashId);
        Map<Long, List<Interval>> orderDurations = getOrdersByBoxId(boxIds);
        return orderDurations.entrySet().stream()
                .filter(entry -> entry.getValue().stream().noneMatch(range -> interval.isInRange(range.getStart()) || interval.isInRange(range.getEnd())))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(BoxFreeNotFoundException::new);
    }

    @Override
    public Set<Interval> getOrderFreeTimes(Long carWashId, Instant time) {
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
                .collect(Collectors.toSet());
    }

    private Map<Long, List<Interval>> getOrdersByBoxId(List<Long> boxIds) {
        List<Order> orders = orderRepository.findByBoxIdIn(boxIds);
        return getOrdersByBoxId(boxIds, orders);
    }

    private Map<Long, List<Interval>> getOrdersByBoxIdFiltered(List<Long> boxIds, Instant start, Instant end) {
        List<Order> orders = orderRepository.findByBoxIdIn(boxIds).stream()
                .filter(o -> o.getStartTime().equals(start) || o.getStartTime().isAfter(start))
                .filter(o -> o.getEndTime().equals(end) || o.getEndTime().isBefore(end))
                .toList();
        return getOrdersByBoxId(boxIds, orders);
    }

    private Map<Long, List<Interval>> getOrdersByBoxId(List<Long> boxIds, List<Order> orders) {
        return boxIds.stream()
                .collect(Collectors.toMap(
                        boxId -> boxId,
                        boxId -> orders.stream()
                                .filter(order -> Objects.equals(order.getBoxId(), boxId))
                                .map(order -> new Interval(order.getStartTime(), order.getEndTime()))
                                .collect(Collectors.toList())));
    }

    private List<Interval> findFreeTimeRanges(List<Interval> busyIntervals, Instant startDateTime, Instant endDateTime) {
        busyIntervals.sort(Comparator.comparing(Interval::getStart));
        List<Interval> freeIntervals = new ArrayList<>();
        if (busyIntervals.isEmpty()) {
            return Collections.singletonList(new Interval(startDateTime, endDateTime));
        }
        if (startDateTime.isBefore(busyIntervals.getFirst().getStart())) {
            freeIntervals.add(new Interval(startDateTime, busyIntervals.getFirst().getStart()));
        }
        for (int i = 0; i < busyIntervals.size() - 1; i++) {
            Interval current = busyIntervals.get(i);
            Interval next = busyIntervals.get(i + 1);
            if (!current.getEnd().equals(next.getStart())
                    && !current.getEnd().isAfter(next.getStart())
                    && !next.getStart().isAfter(endDateTime)) {
                freeIntervals.add(new Interval(current.getEnd(), next.getStart()));
            }
        }
        if (endDateTime.isAfter(busyIntervals.getLast().getEnd())) {
            freeIntervals.add(new Interval(busyIntervals.getLast().getEnd(), endDateTime));
        }
        return freeIntervals;
    }

}

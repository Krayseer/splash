package ru.anykeyers.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.constant.State;
import ru.anykeyers.orderservice.service.CarWashOrderService;

@Service
@RequiredArgsConstructor
public class CarWashOrderServiceImpl implements CarWashOrderService {

    private final OrderRepository orderRepository;

    @Override
    public int getActiveOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, State.WAIT_PROCESS);
    }

    @Override
    public int getProcessingOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, State.PROCESSING);
    }

    @Override
    public int getProcessedOrdersCount(Long carWashId) {
        return orderRepository.countByCarWashIdAndStatus(carWashId, State.PROCESSED);
    }

}

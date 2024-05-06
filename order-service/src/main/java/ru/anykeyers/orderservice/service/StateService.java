package ru.anykeyers.orderservice.service;

import org.springframework.stereotype.Service;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.constant.State;

/**
 * Сервис обработки состояний заказов
 */
@Service
public class StateService {

    /**
     * Получить следующее состояние заказа, в зависимости от текущего
     *
     * @param order заказ
     */
    public State nextState(Order order) {
        return switch (order.getStatus()) {
            case null -> State.WAIT_PROCESS;
            case WAIT_PROCESS -> State.PROCESSING;
            default -> State.PROCESSED;
        };
    }

}

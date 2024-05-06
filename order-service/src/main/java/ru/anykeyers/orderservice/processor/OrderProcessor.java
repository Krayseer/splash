package ru.anykeyers.orderservice.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.domain.constant.State;
import ru.anykeyers.orderservice.service.StateService;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Обработчик, проверяющий состояние заказов
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProcessor {

    /**
     * Размер страницы с заказами
     */
    private static final int PAGE_SIZE = 100;

    /**
     * Периодичность выполнения обработки
     */
    private static final long DELAY = 1000 * 60;

    private final OrderRepository orderRepository;

    private final StateService stateService;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Обработка состояний каждую минуту
     */
    @Scheduled(fixedDelay = DELAY)
    public void verifyOrders() {
        verifyProcessingOrders();
        verifyProcessedOrders();
    }

    /**
     * Проверка заказов, находящихся в обработке
     */
    private void verifyProcessingOrders() {
        verify(State.WAIT_PROCESS, order -> {
            if (order.getStartTime().isBefore(Instant.now()) && Instant.now().isBefore(order.getEndTime())) {
                updateOrderStatus(order);
            }
        });
    }

    /**
     * Проверка завершенных заказов
     */
    private void verifyProcessedOrders() {
        verify(State.PROCESSING, order -> {
            if (Instant.now().isAfter(order.getEndTime())) {
                updateOrderStatus(order);
            }
        });
    }

    /**
     * Обработка заказа
     *
     * @param state     статус заказа
     * @param consumer  обработчик
     */
    private void verify(State state, Consumer<Order> consumer) {
        int i = 0;
        while (true) {
            List<Order> orders = orderRepository.findByStatus(state, PageRequest.of(i++, PAGE_SIZE));
            if (orders.isEmpty()) {
                break;
            }
            executorService.execute(() -> orders.forEach(consumer));
        }
    }

    /**
     * Обновить состояние заказа и сохранить его в БД
     *
     * @param order заказ
     */
    private void updateOrderStatus(Order order) {
        order.setStatus(stateService.nextState(order));
        orderRepository.save(order);
    }

}

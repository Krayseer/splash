package ru.anykeyers.orderservice.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.order.Order;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
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
    @Value("${order.processor.page-size:100}")
    private int PAGE_SIZE;

    /**
     * Периодичность выполнения обработки
     */
    private final static long DELAY = 10 * 6000;

    private final OrderRepository orderRepository;

    private final ExecutorService executorService;

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
        verify(OrderState.WAIT_PROCESS, order -> {
            if (order.getStartTime().isBefore(Instant.now()) && Instant.now().isBefore(order.getEndTime())) {
                order.setStatus(OrderState.PROCESSING);
                orderRepository.save(order);
            }
        });
    }

    /**
     * Проверка завершенных заказов
     */
    private void verifyProcessedOrders() {
        verify(OrderState.PROCESSING, order -> {
            if (Instant.now().isAfter(order.getEndTime())) {
                order.setStatus(OrderState.PROCESSED);
                orderRepository.save(order);
            }
        });
    }

    /**
     * Обработка заказа
     *
     * @param orderState    статус заказа
     * @param consumer      обработчик
     */
    private void verify(OrderState orderState, Consumer<Order> consumer) {
        int i = 0;
        while (true) {
            List<Order> orders = orderRepository.findByStatus(orderState, PageRequest.of(i++, PAGE_SIZE));
            if (orders.isEmpty()) {
                break;
            }
            executorService.execute(() -> orders.forEach(consumer));
        }
    }

}

package ru.anykeyers.orderservice.processor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.orderservice.service.OrderService;

/**
 * Обработчик сообщений, поступающих по Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProcessor {

    private static final String GROUP_ID = "order-group";

    private final OrderService orderService;

    /**
     * Слушатель события назначения работника заказу
     */
    @KafkaListener(topics = MessageQueue.ORDER_EMPLOYEE_APPLY, groupId = GROUP_ID)
    public void receiveOrderApplyEmployee(String orderId) {
        orderService.applyOrderEmployee(Long.getLong(orderId));
    }

    /**
     * Слушатель события об удалении заказа
     */
    @KafkaListener(topics = MessageQueue.ORDER_DELETE, groupId = GROUP_ID)
    public void receiveOrderDelete(String orderId) {
        orderService.deleteOrder(orderId);
    }

}

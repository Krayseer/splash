package ru.anykeyers.orderservice.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.orderservice.service.OrderService;

/**
 * Обработчик сообщений, поступающих по Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProcessor {

    private static final String GROUP_ID = "order-group";

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    /**
     * Слушатель события назначения работника заказу
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_EMPLOYEE_APPLY, groupId = GROUP_ID)
    public void receiveOrderApplyEmployee(String orderDTO) {
        OrderDTO order = objectMapper.readValue(orderDTO, OrderDTO.class);
        orderService.applyOrderEmployee(order.getId());
    }

    /**
     * Слушатель события удаления работника с заказа
     *
     * @param orderId идентификатор заказа
     */
    @KafkaListener(topics = MessageQueue.ORDER_EMPLOYEE_DISAPPOINT, groupId = GROUP_ID)
    public void receiveOrderEmployeeDisappointed(String orderId) {
        orderService.disappointEmployeeFromOrder(Long.parseLong(orderId));
    }

    /**
     * Слушатель события об удалении заказа
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_DELETE, groupId = GROUP_ID)
    public void receiveOrderDelete(String orderDTO) {
        OrderDTO order = objectMapper.readValue(orderDTO, OrderDTO.class);
        orderService.deleteOrder(order.getId());
    }

}

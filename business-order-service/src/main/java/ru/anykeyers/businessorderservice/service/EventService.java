package ru.anykeyers.businessorderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

/**
 * Сервис обработки событий приложения
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Отправить событие о создании заказа
     *
     * @param order данные о заказе
     */
    @SneakyThrows
    public void sendOrderApplyEmployeeEvent(OrderDTO order) {
        kafkaTemplate.send(MessageQueue.ORDER_EMPLOYEE_APPLY, objectMapper.writeValueAsString(order));
    }

    /**
     * Отправить событие об удалении работника с заказа
     *
     * @param orderId идентификатор заказ
     */
    public void sendOrderDisappointEmployeeEvent(String orderId) {
        kafkaTemplate.send(MessageQueue.ORDER_EMPLOYEE_DISAPPOINT, orderId);
    }

    /**
     * Отправить событие об удалении заказа
     *
     * @param order данные о заказе
     */
    @SneakyThrows
    public void sendOrderRemoveEvent(OrderDTO order) {
       kafkaTemplate.send(MessageQueue.ORDER_DELETE, objectMapper.writeValueAsString(order));
    }

}

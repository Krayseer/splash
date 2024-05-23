package ru.anykeyers.businessorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;

/**
 * Сервис обработки событий приложения
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Отправить событие о создании заказа
     *
     * @param orderId идентификатор заказа
     */
    public void sendOrderApplyEmployeeEvent(String orderId) {
        kafkaTemplate.send(MessageQueue.ORDER_EMPLOYEE_APPLY, orderId);
    }

    /**
     * Отправить событие об удалении заказа
     *
     * @param orderId идентификатор заказа
     */
    public void sendOrderRemoveEvent(Long orderId) {
       kafkaTemplate.send(MessageQueue.ORDER_DELETE, String.valueOf(orderId));
    }

}

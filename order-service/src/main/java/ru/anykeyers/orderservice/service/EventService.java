package ru.anykeyers.orderservice.service;

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
     * @param orderDTO данные о заказе
     */
    @SneakyThrows
    public void sendOrderCreatedEvent(OrderDTO orderDTO) {
        String message = objectMapper.writeValueAsString(orderDTO);
        kafkaTemplate.send(MessageQueue.ORDER_CREATE, message);
    }

}

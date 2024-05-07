package ru.anykeyers.businessorderservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.businessorderservice.service.OrderService;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    /**
     * Слушатель создания заказов
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_CREATE, groupId = "account-group")
    public void receiveOrderCreate(String orderMessage) {
        OrderDTO order = objectMapper.readValue(orderMessage, new TypeReference<>() {
        });
        orderService.processOrder(order);
    }

}

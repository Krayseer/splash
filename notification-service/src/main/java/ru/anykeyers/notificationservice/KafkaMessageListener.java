package ru.anykeyers.notificationservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.notificationservice.service.EmployeeService;
import ru.anykeyers.notificationservice.service.OrderService;

/**
 * Слушатель сообщений по Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    private final EmployeeService employeeService;

    /**
     * Слушатель создания заказов
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_CREATE, groupId = "account-group")
    public void receiveOrderCreate(String orderMessage) {
        OrderDTO order = objectMapper.readValue(orderMessage, new TypeReference<>() {});
        orderService.notifyOrderCreate(order);
    }

    @SneakyThrows
    @KafkaListener(topics = MessageQueue.INVITATION_APPLY, groupId = "account-group")
    public void receiveApplyInvitation(String invitationMessage) {
        EmployeeDTO employee = objectMapper.readValue(invitationMessage, new TypeReference<>() {});
        employeeService.notifyEmployeeApply(employee);
    }

}

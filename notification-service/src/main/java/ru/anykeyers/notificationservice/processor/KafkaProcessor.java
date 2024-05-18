package ru.anykeyers.notificationservice.processor;

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

/**
 * Обработчик сообщений с Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProcessor { //TODO: УВЕДОМЛЕНИЯ ДОЛЖНЫ СОХРАНЯТЬСЯ В БД

    private static final String GROUP_ID = "notification-group";

    private final ObjectMapper objectMapper;

    private final OrderProcessor orderProcessor;

    private final EmployeeProcessor employeeProcessor;

    /**
     * Слушатель создания заказов
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_CREATE, groupId = GROUP_ID)
    public void receiveOrderCreate(String orderMessage) {
        OrderDTO order = objectMapper.readValue(orderMessage, new TypeReference<>() {});
        orderProcessor.processOrderCreate(order);
    }

    /**
     * Слушатель одобрения заявки работника
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.INVITATION_APPLY, groupId = GROUP_ID)
    public void receiveApplyInvitation(String invitationMessage) {
        EmployeeDTO employee = objectMapper.readValue(invitationMessage, new TypeReference<>() {});
        employeeProcessor.processEmployeeApply(employee);
    }

}

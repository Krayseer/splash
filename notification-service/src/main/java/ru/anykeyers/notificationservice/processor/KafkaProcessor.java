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
import ru.anykeyers.notificationservice.processor.order.OrderProcessor;

/**
 * Обработчик сообщений с Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProcessor {

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

    /**
     * Слушатель события назначения работника заказу
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_EMPLOYEE_APPLY, groupId = GROUP_ID)
    public void receiveOrderApplyEmployee(String orderDTO) {
        OrderDTO order = objectMapper.readValue(orderDTO, OrderDTO.class);
        orderProcessor.processOrderEmployeeApply(order);
    }

    /**
     * Слушатель события об удалении заказа
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.ORDER_DELETE, groupId = GROUP_ID)
    public void receiveOrderDelete(String orderDTO) {
        OrderDTO order = objectMapper.readValue(orderDTO, OrderDTO.class);
        orderProcessor.processOrderDelete(order);
    }

}

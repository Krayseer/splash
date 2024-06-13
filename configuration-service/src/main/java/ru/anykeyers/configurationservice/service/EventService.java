package ru.anykeyers.configurationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;

/**
 * Сервис отправки уведомлений
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Отправить уведомление о принятии приглашения на работу
     *
     * @param employeeDTO данные о работнике
     */
    @SneakyThrows
    public void sendEmployeeApplyEvent(EmployeeDTO employeeDTO) {
        kafkaTemplate.send(MessageQueue.INVITATION_APPLY, objectMapper.writeValueAsString(employeeDTO));
    }

}

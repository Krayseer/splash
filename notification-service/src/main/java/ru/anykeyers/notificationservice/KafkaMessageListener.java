package ru.anykeyers.notificationservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.anykeyers.notificationservice.service.EmailService;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.dto.email.EmailDTO;

/**
 * Слушатель сообщений по Kafka
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final ObjectMapper objectMapper;

    private final EmailService emailService;

    /**
     * Слушатель обработки отправки почтовых сообщений
     */
    @SneakyThrows
    @KafkaListener(topics = MessageQueue.EMAIL_SENDER, groupId = "account-group")
    public void receiveEmailSendMessage(String message) {
        EmailDTO emailDTO = objectMapper.readValue(message, new TypeReference<>() {
        });
        emailService.sendMessage(emailDTO.getEmailAddress(), emailDTO.getEmailContent());
    }

}

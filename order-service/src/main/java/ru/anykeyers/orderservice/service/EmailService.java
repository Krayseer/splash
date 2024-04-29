package ru.anykeyers.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.dto.UserDTO;
import ru.anykeyers.commonsapi.dto.email.EmailAddress;
import ru.anykeyers.commonsapi.dto.email.EmailContent;
import ru.anykeyers.commonsapi.dto.email.EmailDTO;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.orderservice.service.remote.RemoteConfigurationService;
import ru.anykeyers.orderservice.service.remote.RemoteUserService;

/**
 * Сервис отправки сообщений
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final RemoteUserService remoteUserService;

    private final RemoteConfigurationService remoteConfigurationService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    /**
     * Отправить сообщение о создании заявки
     *
     * @param order данные о заявке
     */
    @SneakyThrows
    public void sendEmail(Order order) {
        UserDTO userDTO = remoteUserService.getUser(order.getUsername());
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(order.getCarWashId());
        EmailContent<String> message = new EmailContent<>(userDTO.getEmail(),
                String.format("""
                        Ваш заказ успешно принят.
                        Автомойка: %s
                        Бокс: %s
                        Время: %s
                        """, configurationDTO.getName(), order.getBoxId(), order.getTime())
        );
        EmailDTO emailDTO = new EmailDTO(new EmailAddress(userDTO.getEmail()), message);
        kafkaTemplate.send(MessageQueue.EMAIL_SENDER, objectMapper.writeValueAsString(emailDTO));
    }

}

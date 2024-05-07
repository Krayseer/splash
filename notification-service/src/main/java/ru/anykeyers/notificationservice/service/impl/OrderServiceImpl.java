package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.email.EmailAddress;
import ru.anykeyers.commonsapi.domain.dto.email.EmailContent;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.notificationservice.service.EmailService;
import ru.anykeyers.notificationservice.service.OrderService;

/**
 * Реализация сервиса отправки уведомлений о создании заказа
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final EmailService emailService;

    private final RemoteUserService remoteUserService;

    private final RemoteConfigurationService remoteConfigurationService;

    @Override
    public void notifyOrderCreate(OrderDTO order) {
        UserDTO user = remoteUserService.getUser(order.getUsername());
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(order.getCarWashId());
        EmailContent<String> content = new EmailContent<>(user.getEmail(),
                String.format("""
                        Ваш заказ успешно принят.
                        Автомойка: %s
                        Бокс: %s
                        Время: %s
                        """, configurationDTO.getName(), order.getBoxId(), order.getStartTime())
        );
        emailService.sendMessage(new EmailAddress(user.getEmail()), content);
    }

}

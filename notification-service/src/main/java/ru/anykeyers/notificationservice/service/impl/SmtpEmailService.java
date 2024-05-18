package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.UserSettingDTO;
import ru.anykeyers.notificationservice.domain.Notification;
import ru.anykeyers.notificationservice.service.NotificationService;

/**
 * Реализация сервиса отправки сообщений по протоколу SMTP
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpEmailService implements NotificationService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void notify(UserDTO user, Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject(notification.getSubject());
        message.setText(notification.getMessage());
        try {
            emailSender.send(message);
            log.info("Send message to ({}) with content: {}", user.getEmail(), notification.getMessage());
        } catch (MailAuthenticationException mailAuthenticationException) {
            log.error("Failed send message to: {}", user.getEmail());
        }
    }

    @Override
    public boolean supports(UserSettingDTO userSetting) {
        return userSetting.isEmailEnabled();
    }
}

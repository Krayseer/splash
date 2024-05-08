package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.anykeyers.notificationservice.domain.EmailAddress;
import ru.anykeyers.notificationservice.domain.EmailContent;
import ru.anykeyers.notificationservice.service.EmailService;

/**
 * Реализация сервиса отправки сообщений по протоколу SMTP
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMessage(EmailAddress emailAddress, EmailContent<?> emailContent) {
        log.info("Send message to <{}> with content: {}", emailAddress, emailContent);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(emailAddress.getAddress());
        message.setSubject(emailContent.getSubject());
        message.setText((String) emailContent.getContent());
        emailSender.send(message);
    }

}

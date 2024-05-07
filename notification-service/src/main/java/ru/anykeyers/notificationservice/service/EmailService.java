package ru.anykeyers.notificationservice.service;

import ru.anykeyers.commonsapi.domain.dto.email.EmailAddress;
import ru.anykeyers.commonsapi.domain.dto.email.EmailContent;

/**
 * Сервис отправки сообщений по email
 */
public interface EmailService {

    /**
     * Отправить сообщение
     *
     * @param emailAddress  адрес, куда нужно отправить сообщение
     * @param emailContent  данные сообщения
     */
    void sendMessage(EmailAddress emailAddress, EmailContent<?> emailContent);

}

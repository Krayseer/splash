package ru.anykeyers.notificationservice.service;

import ru.anykeyers.notificationservice.domain.EmailAddress;
import ru.anykeyers.notificationservice.domain.EmailContent;

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

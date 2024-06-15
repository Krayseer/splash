package ru.anykeyers.notificationservice.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.notificationservice.domain.Notification;

/**
 * Тесты для {@link SmtpEmailService}
 */
@ExtendWith(MockitoExtension.class)
class SmtpEmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private SmtpEmailService smtpEmailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> notificationCaptor;

    /**
     * Тест отправки уведомления по email
     */
    @Test
    void testNotify() {
        Notification notification = new Notification("subject", "body");
        UserDTO user = UserDTO.builder().email("email@email.com").build();
        smtpEmailService.notify(user, notification);
        Mockito.verify(mailSender).send(notificationCaptor.capture());
        SimpleMailMessage mailMessage = notificationCaptor.getValue();
        Assertions.assertArrayEquals(new String[]{"email@email.com"}, mailMessage.getTo());
        Assertions.assertEquals("subject", mailMessage.getSubject());
        Assertions.assertEquals("body", mailMessage.getText());
    }

}
package ru.anykeyers.notificationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.notificationservice.domain.PushNotificationDTO;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @GetMapping
    public List<PushNotificationDTO> getNotifications(@AuthenticationPrincipal Jwt jwt) {
        return pushNotificationService.getNotifications(jwt.getSubject());
    }

}

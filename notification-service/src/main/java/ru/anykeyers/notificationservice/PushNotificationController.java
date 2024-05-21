package ru.anykeyers.notificationservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.notificationservice.domain.ControllerName;
import ru.anykeyers.notificationservice.domain.push.PushNotificationDTO;
import ru.anykeyers.notificationservice.service.impl.PushNotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.PUSH_NAME)
@Tag(name = "Обработка уведомлений")
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @Operation(summary = "Получить все уведомления авторизованного пользователя")
    @GetMapping
    public List<PushNotificationDTO> getNotifications(@AuthenticationPrincipal Jwt jwt) {
        return pushNotificationService.getNotifications(jwt.getSubject());
    }

}

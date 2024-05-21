package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.InvitationDTO;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.service.InvitationService;

import java.util.List;

/**
 * REST контроллер запросов на обработку приглашений
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.INVITATION_NAME)
@Tag(name = "Обработка отправки приглашений")
public class InvitationController {

    private final InvitationService invitationService;

    @Operation(summary = "Получить список приглашений авторизованного пользователя")
    @GetMapping
    public List<InvitationDTO> getInvitations(@AuthenticationPrincipal Jwt jwt) {
        return invitationService.getInvitations(jwt.getSubject());
    }

    @Operation(summary = "Получить список приглашений, отправленных от лица автомойки")
    @GetMapping("/{carWashId}")
    public List<InvitationDTO> getInvitation(@PathVariable Long carWashId) {
        return invitationService.getInvitations(carWashId);
    }

    @Operation(summary = "Добавление данных об отправленном приглашении")
    @PostMapping
    public void addInvitation(@RequestBody InvitationDTO invitation) {
        invitationService.addInvitation(invitation);
    }

    @Operation(summary = "Подтверждение приглашения")
    @PostMapping("/{id}")
    public void applyInvitation(@PathVariable Long id) {
        invitationService.applyInvitation(id);
    }

}

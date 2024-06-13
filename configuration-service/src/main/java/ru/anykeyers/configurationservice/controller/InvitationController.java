package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.invitation.InvitationDTO;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.domain.invitation.InvitationRequest;
import ru.anykeyers.configurationservice.service.InvitationService;

import java.util.List;

/**
 * REST контроллер запросов на обработку приглашений
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.INVITATION_CONTROLLER)
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

    @Operation(summary = "Получить список отправленных не обработнных приглашений автомойки")
    @GetMapping("/{carWashId}/sent")
    public List<InvitationDTO> getSentInvitations(@PathVariable Long carWashId) {
        return invitationService.getInvitations(carWashId, InvitationState.SENT);
    }

    @Operation(summary = "Получить список принятых приглашений автомойки")
    @GetMapping("/{carWashId}/accepted")
    public List<InvitationDTO> getAcceptedInvitations(@PathVariable Long carWashId) {
        return invitationService.getInvitations(carWashId, InvitationState.ACCEPTED);
    }

    @Operation(summary = "Получить список отклоненных приглашений автомойки")
    @GetMapping("/{carWashId}/rejected")
    public List<InvitationDTO> getRejectedInvitations(@PathVariable Long carWashId) {
        return invitationService.getInvitations(carWashId, InvitationState.REJECTED);
    }

    @Operation(summary = "Добавление данных об отправленном приглашении")
    @PostMapping
    public void addInvitation(@RequestBody InvitationRequest invitation) {
        invitationService.addInvitation(invitation);
    }

    @Operation(summary = "Подтверждение приглашения")
    @PostMapping("/apply/{id}")
    public void applyInvitation(@PathVariable Long id) {
        invitationService.applyInvitation(id);
    }

    @Operation(summary = "Отклонить приглашение")
    @PostMapping("/decline/{id}")
    public void declineInvitation(@PathVariable Long id) {
        invitationService.declineInvitation(id);
    }

    @Operation(summary = "Удалить приглашение")
    @DeleteMapping("/{id}")
    public void deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
    }

}

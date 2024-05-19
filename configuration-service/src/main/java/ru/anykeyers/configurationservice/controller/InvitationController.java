package ru.anykeyers.configurationservice.controller;

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
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    public List<InvitationDTO> getInvitations(@AuthenticationPrincipal Jwt jwt) {
        return invitationService.getInvitations(jwt.getSubject());
    }

    @GetMapping("/{carWashId}")
    public List<InvitationDTO> getInvitation(@PathVariable Long carWashId) {
        return invitationService.getInvitations(carWashId);
    }

    @PostMapping
    public void addInvitation(@RequestBody InvitationDTO invitation) {
        invitationService.addInvitation(invitation);
    }

    @PostMapping("/{id}")
    public void applyInvitation(@PathVariable Long id) {
        invitationService.applyInvitation(id);
    }

}

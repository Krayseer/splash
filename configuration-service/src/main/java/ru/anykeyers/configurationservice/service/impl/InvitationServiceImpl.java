package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.invitation.InvitationDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.invitation.Invitation;
import ru.anykeyers.configurationservice.domain.invitation.InvitationRequest;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.exception.InvitationNotFoundException;
import ru.anykeyers.configurationservice.domain.invitation.InvitationMapper;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.repository.InvitationRepository;
import ru.anykeyers.configurationservice.service.EmployeeService;
import ru.anykeyers.configurationservice.service.InvitationService;

import java.util.List;

/**
 * Реализация сервиса обработки приглашений
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final EmployeeService employeeService;

    private final RemoteUserService remoteUserService;

    private final InvitationRepository invitationRepository;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<InvitationDTO> getInvitations(String username) {
        return InvitationMapper.toDTO(invitationRepository.findByUsername(username));
    }

    @Override
    public List<InvitationDTO> getInvitations(Long carWashId) {
        return InvitationMapper.toDTO(invitationRepository.findByConfigurationId(carWashId));
    }

    @Override
    public List<InvitationDTO> getInvitations(Long carWashId, InvitationState invitationState) {
        return InvitationMapper.toDTO(
                invitationRepository.findByConfigurationIdAndInvitationState(carWashId, invitationState)
        );
    }

    @Override
    public void addInvitation(InvitationRequest invitationRequest) {
        Invitation invitation = InvitationMapper.createInvitation(invitationRequest);
        Configuration configuration = configurationRepository.findById(invitationRequest.getCarWashId()).orElseThrow(
                () -> new ConfigurationNotFoundException(invitationRequest.getCarWashId())
        );
        invitation.setConfiguration(configuration);
        invitationRepository.save(invitation);
        log.info("Send invitation: {}", invitation);
    }

    @Override
    @SneakyThrows
    public void applyInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(
                () -> new InvitationNotFoundException(invitationId)
        );
        invitation.setInvitationState(InvitationState.ACCEPTED);
        UserDTO user = remoteUserService.getUser(invitation.getUsername());
        employeeService.addCarWashEmployee(invitation.getConfiguration(), user.getId());
        log.info("Apply invitation: {}", invitation);
    }

    @Override
    public void declineInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(
                () -> new InvitationNotFoundException(id)
        );
        invitation.setInvitationState(InvitationState.REJECTED);
        invitationRepository.save(invitation);
        log.info("Decline invitation: {}", invitation);
    }

    @Override
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
        log.info("Delete invitation: {}", id);
    }

}

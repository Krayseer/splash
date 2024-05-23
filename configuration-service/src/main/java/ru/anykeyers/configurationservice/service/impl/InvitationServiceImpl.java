package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.InvitationDTO;
import ru.anykeyers.configurationservice.domain.invitation.Invitation;
import ru.anykeyers.configurationservice.exception.InvitationNotFoundException;
import ru.anykeyers.configurationservice.domain.invitation.InvitationMapper;
import ru.anykeyers.configurationservice.repository.InvitationRepository;
import ru.anykeyers.configurationservice.service.EmployeeService;
import ru.anykeyers.configurationservice.service.InvitationService;

import java.util.List;

/**
 * Реализация сервиса обработки приглашений
 */
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final EmployeeService employeeService;

    private final InvitationRepository invitationRepository;

    @Override
    public List<InvitationDTO> getInvitations(String username) {
        return InvitationMapper.createDTO(invitationRepository.findByUsername(username));
    }

    @Override
    public List<InvitationDTO> getInvitations(Long carWashId) {
        return InvitationMapper.createDTO(invitationRepository.findByCarWashId(carWashId));
    }

    @Override
    public void addInvitation(InvitationDTO invitationDTO) {
        Invitation invitation = InvitationMapper.createInvitation(invitationDTO);
        invitationRepository.save(invitation);
    }

    @Override
    @SneakyThrows
    public void applyInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(
                () -> new InvitationNotFoundException(invitationId)
        );
        employeeService.addCarWashEmployee(invitation.getCarWashId(), invitationId);
    }

}

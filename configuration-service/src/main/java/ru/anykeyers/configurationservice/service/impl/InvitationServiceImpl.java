package ru.anykeyers.configurationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.InvitationDTO;
import ru.anykeyers.configurationservice.domain.entity.Invitation;
import ru.anykeyers.configurationservice.repository.InvitationRepository;
import ru.anykeyers.configurationservice.service.EmployeeService;
import ru.anykeyers.configurationservice.service.InvitationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final EmployeeService employeeService;

    private final InvitationRepository invitationRepository;

    @Override
    public List<InvitationDTO> getInvitations(String username) {
        return createResponse(invitationRepository.findByUsername(username));
    }

    @Override
    public List<InvitationDTO> getInvitations(Long carWashId) {
        return createResponse(invitationRepository.findByCarWashId(carWashId));
    }

    @Override
    public void addInvitation(InvitationDTO invitationDTO) {
        Invitation invitation = Invitation.builder()
                .username(invitationDTO.getUsername())
                .carWashId(invitationDTO.getCarWashId())
                .roles(invitationDTO.getRoles())
                .build();
        invitationRepository.save(invitation);
    }

    @Override
    @SneakyThrows
    public void applyInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(
                () -> new RuntimeException("Invitation with id " + invitationId + " not found")
        );
        employeeService.addCarWashEmployee(invitation.getCarWashId(), invitationId);
    }

    private InvitationDTO createResponse(Invitation invitation) {
        return new InvitationDTO(invitation.getUsername(), invitation.getCarWashId(), invitation.getRoles());
    }

    private List<InvitationDTO> createResponse(List<Invitation> invitations) {
        return invitations.stream().map(this::createResponse).toList();
    }

}

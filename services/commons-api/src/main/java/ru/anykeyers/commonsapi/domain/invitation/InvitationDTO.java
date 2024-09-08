package ru.anykeyers.commonsapi.domain.invitation;

import ru.anykeyers.commonsapi.domain.configuration.ConfigurationSimpleDTO;
import ru.anykeyers.commonsapi.domain.user.UserDTO;

import java.util.List;

public record InvitationDTO(Long id,
                            UserDTO user,
                            ConfigurationSimpleDTO configuration,
                            List<String> roles,
                            InvitationState invitationState) {
}

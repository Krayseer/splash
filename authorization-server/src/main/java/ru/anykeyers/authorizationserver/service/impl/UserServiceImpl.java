package ru.anykeyers.authorizationserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.anykeyers.authorizationserver.domain.UserRequest;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.authorizationserver.domain.entity.User;
import ru.anykeyers.authorizationserver.repository.RoleRepository;
import ru.anykeyers.authorizationserver.repository.UserRepository;
import ru.anykeyers.authorizationserver.service.UserService;
import ru.anykeyers.commonsapi.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        Role userRoles = roleRepository.findAll().getFirst();
        User user = User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .password("{noop}" + userRequest.getPassword())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .roleList(Collections.singletonList(userRoles))
                .photoUrl(userRequest.getPhotoUrl())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

}

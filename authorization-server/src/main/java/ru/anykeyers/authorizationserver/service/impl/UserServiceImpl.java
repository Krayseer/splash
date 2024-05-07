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
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt().toString())
                .build();
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .password("{noop}" + userRequest.getPassword())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .roleList(new ArrayList<>() {{ roleRepository.findByRoleCode("ROLE_USER"); }})
                .photoUrl(userRequest.getPhotoUrl())
                .createdAt(Instant.now())
                .build();
        userRepository.save(user);
    }

    @Override
    public void setUserRoles(Long userId, List<String> roles) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        Set<Role> updatedRoles = new HashSet<>();
        updatedRoles.addAll(user.getRoleList());
        updatedRoles.addAll(roleRepository.findByRoleCodeIn(roles));
        user.setRoleList(new ArrayList<>(updatedRoles));
        userRepository.save(user);
    }

}

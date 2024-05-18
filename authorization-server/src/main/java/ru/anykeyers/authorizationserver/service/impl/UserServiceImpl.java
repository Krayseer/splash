package ru.anykeyers.authorizationserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.domain.user.UserRequest;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.authorizationserver.domain.user.User;
import ru.anykeyers.authorizationserver.domain.user.UserSetting;
import ru.anykeyers.authorizationserver.repository.RoleRepository;
import ru.anykeyers.authorizationserver.repository.UserRepository;
import ru.anykeyers.authorizationserver.service.UserService;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.UserSettingDTO;
import ru.anykeyers.commonsapi.service.RemoteStorageService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final RemoteStorageService remoteStorageService;

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
                .roles(user.getRoleList().stream().map(Role::getRoleCode).toList())
                .createdAt(user.getCreatedAt().toString())
                .userSettingDTO(
                        new UserSettingDTO(user.getUserSetting().isPushEnabled(), user.getUserSetting().isEmailEnabled())
                )
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
                .photoUrl(null)
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

    @Override
    public void addPhoto(String username, MultipartFile photo) {
        ResponseEntity<String> photoUrlResponse = remoteStorageService.uploadPhoto(photo);
        if (!photoUrlResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Cannot upload photo. Storage service returned status {}", photoUrlResponse.getStatusCode());
            return;
        }
        User user = userRepository.findUserByUsername(username);
        user.setPhotoUrl(photoUrlResponse.getBody());
        userRepository.save(user);
    }

    @Override
    public void setUserSetting(String username, UserSettingDTO userSetting) {
        User user = userRepository.findUserByUsername(username);
        UserSetting setting = user.getUserSetting() == null ? new UserSetting() : user.getUserSetting();
        setting.setEmailEnabled(userSetting.isEmailEnabled());
        setting.setPushEnabled(userSetting.isPushEnabled());
        user.setUserSetting(setting);
        userRepository.save(user);
    }

}

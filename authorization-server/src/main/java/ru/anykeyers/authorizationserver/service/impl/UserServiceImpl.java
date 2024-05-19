package ru.anykeyers.authorizationserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.domain.user.UserMapper;
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
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(id.toString())
        );
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).toList();
    }

    @Override
    public List<UserDTO> getUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds).stream().map(UserMapper::toDTO).toList();
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);
        user.setRoleList(new ArrayList<>() {{ roleRepository.findByRoleCode("ROLE_USER"); }});
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

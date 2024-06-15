package ru.anykeyers.authorizationserver.domain.user;

import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserSettingDTO;

import java.time.Instant;

/**
 * Маппер для пользователя
 */
public class UserMapper {

    /**
     * Создать пользователя
     *
     * @param userRequest регистрационные данные
     */
    public static User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .password("{noop}" + userRequest.getPassword())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .photoUrl(null)
                .userSetting(new UserSetting())
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать DTO для пользователя
     *
     * @param user пользователь
     */
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoleList().stream().map(Role::getRoleCode).toList())
                .createdAt(user.getCreatedAt().toString())
                .photoUrl(user.getPhotoUrl())
                .userSetting(
                        new UserSettingDTO(user.getUserSetting().isPushEnabled(), user.getUserSetting().isEmailEnabled())
                )
                .build();
    }

}

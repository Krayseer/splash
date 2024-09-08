package ru.anykeyers.commonsapi.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private Instant createdAt;
    private List<String> roles;
    private UserSettingDTO userSetting;
}

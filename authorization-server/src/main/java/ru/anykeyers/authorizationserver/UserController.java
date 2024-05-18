package ru.anykeyers.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.domain.user.UserRequest;
import ru.anykeyers.authorizationserver.service.UserService;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.domain.dto.UserSettingDTO;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDTO getUser(Principal principal) {
        return userService.getUser(principal.getName());
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PostMapping
    public void saveUser(@RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
    }

    @GetMapping("/roles")
    public List<String> getUserRoles() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/roles")
    public void setUserRoles(@RequestParam("userId") Long userId, @RequestBody List<String> roles) {
        userService.setUserRoles(userId, roles);
    }

    @PostMapping("/photo")
    public void addPhoto(@RequestParam("photo") MultipartFile photo, Principal principal) {
        userService.addPhoto(principal.getName(), photo);
    }

    @PostMapping("/setting")
    public void setUserSettings(@RequestBody UserSettingDTO userSetting, Principal principal) {
        userService.setUserSetting(principal.getName(), userSetting);
    }

}

package ru.anykeyers.authorizationserver.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.authorizationserver.web.ControllerName;
import ru.anykeyers.authorizationserver.web.dto.UserRegisterRequest;
import ru.anykeyers.authorizationserver.service.UserService;
import ru.anykeyers.commonsapi.domain.user.User;
import ru.anykeyers.commonsapi.domain.user.UserInfo;
import ru.anykeyers.commonsapi.utils.JwtUtils;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BASE_URL)
public class UserController {

    private final UserService userService;

    @GetMapping
    public User getUser(@AuthenticationPrincipal Jwt jwt) {
        return JwtUtils.extractUser(jwt);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/by-id/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping
    public void createUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        userService.addUser(userRegisterRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody UserInfo userUpdateRequest) {
        userService.updateUser(JwtUtils.extractUser(jwt), userUpdateRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(UUID id) {
        userService.deleteUser(id);
    }

}

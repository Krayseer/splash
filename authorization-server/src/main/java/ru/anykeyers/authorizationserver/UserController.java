package ru.anykeyers.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.authorizationserver.domain.UserRequest;
import ru.anykeyers.authorizationserver.service.UserService;
import ru.anykeyers.commonsapi.dto.UserDTO;

import java.security.Principal;

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

    @GetMapping("/register")
    public void saveUser(@RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
    }

}

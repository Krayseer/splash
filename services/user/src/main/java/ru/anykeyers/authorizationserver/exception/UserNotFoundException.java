package ru.anykeyers.authorizationserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Исключение о несуществовании пользователя
 */
public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "User not found with id: " + id);
    }

    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "User not found with username: " + username);
    }

}

package ru.anykeyers.authorizationserver.exception;

/**
 * Исключение об уже существующем пользователе
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username);
    }

}

package ru.anykeyers.configurationservice.exception;

/**
 * У пользователя не имеется автомоек
 */
public class UserNotFoundConfigurationException extends RuntimeException {

    public UserNotFoundConfigurationException(String username) {
        super(String.format("Configuration for user %s not found", username));
    }

}

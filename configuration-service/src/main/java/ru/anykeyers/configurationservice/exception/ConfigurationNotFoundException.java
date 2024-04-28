package ru.anykeyers.configurationservice.exception;

/**
 * Ошибка отстутствия автомойки
 */
public class ConfigurationNotFoundException extends RuntimeException {

    public ConfigurationNotFoundException(Long id) {
        super("Configuration with id " + id + " not found");
    }

}

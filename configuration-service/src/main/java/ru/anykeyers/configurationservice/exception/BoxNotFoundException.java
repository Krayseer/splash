package ru.anykeyers.configurationservice.exception;

/**
 * Исключение о несуществовании бокса
 */
public class BoxNotFoundException extends RuntimeException {

    public BoxNotFoundException(Long boxId) {
        super("Box not found: " + boxId);
    }

}

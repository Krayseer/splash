package ru.anykeyers.orderservice.exception;

/**
 * Исключение, что не имеется свободных боксов
 */
public class BoxFreeNotFoundException extends RuntimeException {

    public BoxFreeNotFoundException() {
        super("Free box not found");
    }

}

package ru.anykeyers.orderservice.exception;

/**
 * Ошибка присутствия активного заказа у пользователя
 */
public class UserAlreadyExistsOrderException extends RuntimeException {

    public UserAlreadyExistsOrderException(String username) {
        super(String.format("User '%s' already exists active order", username));
    }

}

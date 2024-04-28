package ru.anykeyers.orderservice.exception;

/**
 * Ошибка получения заказа
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String username) {
        super(String.format("User %s has not order", username));
    }

}

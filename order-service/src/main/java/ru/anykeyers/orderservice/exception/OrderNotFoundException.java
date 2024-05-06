package ru.anykeyers.orderservice.exception;

/**
 * Ошибка получения заказа
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super(String.format("Order with id %d not found", id));
    }

}

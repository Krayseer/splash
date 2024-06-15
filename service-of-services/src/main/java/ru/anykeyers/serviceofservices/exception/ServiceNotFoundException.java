package ru.anykeyers.serviceofservices.exception;

/**
 * Исключение, что несуществует услгуа
 */
public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(Long id) {
        super("Service not found: " + id);
    }

}

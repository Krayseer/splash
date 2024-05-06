package ru.anykeyers.configurationservice.exception;

/**
 * Исключение, информирующее, что услуг не найдено
 */
public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(Long carWashId) {
        super(String.format("Services not found for car wash with id %s", carWashId));
    }

}

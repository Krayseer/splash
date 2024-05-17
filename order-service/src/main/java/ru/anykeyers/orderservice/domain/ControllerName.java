package ru.anykeyers.orderservice.domain;

/**
 * Названия контроллеров
 */
public final class ControllerName {

    /**
     * Базовое название контроллеров
     */
    public static final String BASE_NAME = "/order";

    /**
     * Название контроллера для обработки заказов пользователей
     */
    public static final String USER_ORDER_NAME = BASE_NAME + "/user";

    /**
     * Название контроллера для обработки заказов автомоек
     */
    public static final String CAR_WASH_ORDER_NAME = BASE_NAME + "/car-wash";

}

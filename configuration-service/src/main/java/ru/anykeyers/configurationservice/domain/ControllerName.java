package ru.anykeyers.configurationservice.domain;

/**
 * Название контроллера
 */
public final class ControllerName {

    /**
     * Базовый контроллер
     */
    public static final String BASE_CONTROLLER = "/car-wash";

    /**
     * Контроллер конфигурации автомоек
     */
    public static final String CONFIGURATION_CONTROLLER = BASE_CONTROLLER + "/configuration";

    /**
     * Контроллер работников
     */
    public static final String EMPLOYEE_CONTROLLER = BASE_CONTROLLER + "/employee";

    /**
     * Контроллер боксов
     */
    public static final String BOX_CONTROLLER = BASE_CONTROLLER + "/box";

    /**
     * Контроллер приглашений
     */
    public static final String INVITATION_CONTROLLER = BASE_CONTROLLER + "/invitation";

}

package ru.anykeyers.commonsapi.domain;

import lombok.*;

/**
 * Конфигурация удаленных сервисов
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteConfiguration {

    /**
     * URL сервиса обработки конфигураций автомоек
     */
    public String configurationServiceUrl;

    /**
     * URL сервиса обработки услуг
     */
    public String serviceOfServicesUrl;

    /**
     * URL сервиса обработки пользователей
     */
    public String userServiceUrl;

    /**
     * URL сервиса хранилища
     */
    public String storageServiceUrl;

}

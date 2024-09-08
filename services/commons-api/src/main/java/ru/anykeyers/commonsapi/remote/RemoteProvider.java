package ru.anykeyers.commonsapi.remote;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Удаленный провайдер
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "remote-provider")
public class RemoteProvider {

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
    /**
     * URL сервиса обработки заказов
     */
    public String orderServiceUrl;

}

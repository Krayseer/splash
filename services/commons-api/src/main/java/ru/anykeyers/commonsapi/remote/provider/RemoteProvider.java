package ru.anykeyers.commonsapi.remote.provider;

import org.springframework.web.client.RestTemplate;

/**
 * Провайдер удаленных ресурсов
 */
public interface RemoteProvider {

    /**
     * @return базовый URL удаленного ресурса
     */
    String getBaseUrl();

    /**
     * @return REST-клиент для подключения к удаленному ресурсу
     */
    RestTemplate getRestTemplate();

}

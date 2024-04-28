package ru.anykeyers.authorizationserver.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Конфигурация приложения
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    private OAuthProvider authProvider;

    /**
     * Провайдер OAuth2 авторизации
     */
    @Getter
    @Setter
    private static class OAuthProvider {

        /**
         * Идентификатор клиента регистрации
         */
       private String registrationId;

        /**
         * Название провайдера
         */
        private String clientName;

        /**
         * Идентификатор клиента
         */
        private String clientId;

        /**
         * Секрет клиента
         */
        private String clientSecret;

        /**
         * URL адреса авторизации
         */
        private String authorizationUri;

        /**
         * URL адреса получения токена
         */
        private String tokenUri;

        /**
         * URL адреса переадресации
         */
        private String redirectUri;

        /**
         * URL получения информации по пользователю
         */
        private String userInfoUri;

    }

}

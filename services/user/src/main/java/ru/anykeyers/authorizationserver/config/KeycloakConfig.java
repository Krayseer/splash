package ru.anykeyers.authorizationserver.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Keycloak
 */
@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(@Value("${KEYCLOAK_SERVER_URL}") String serverUrl,
                             @Value("${KEYCLOAK_REALM}") String realm,
                             @Value("${KEYCLOAK_CLIENT_ID}") String clientId,
                             @Value("${KEYCLOAK_CLIENT_SECRET}") String clientSecret,
                             @Value("${KEYCLOAK_USERNAME}") String username,
                             @Value("${KEYCLOAK_PASSWORD}") String password) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

}

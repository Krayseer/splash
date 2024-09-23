package ru.anykeyers.authorizationserver.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Конфигуратор для Keycloak
 */
@Component
@RequiredArgsConstructor
public class KeycloakConfigurator {

    private final Keycloak keycloak;

    @Value("${KEYCLOAK_REALM}")
    private String realmName;

    /**
     * Получить ресурс пользователей
     */
    public UsersResource getUsersResource() {
        return keycloak.realm(realmName).users();
    }

    /**
     * Создать данные о пароле пользователя
     *
     * @param password пароль пользователя
     */
    public List<CredentialRepresentation> createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return Collections.singletonList(passwordCredentials);
    }

}

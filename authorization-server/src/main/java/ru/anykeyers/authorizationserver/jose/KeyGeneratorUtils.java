package ru.anykeyers.authorizationserver.jose;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Утилиты для генерации ключей
 */
final class KeyGeneratorUtils {

    /**
     * Сгенерировать ключ RSA
     */
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}

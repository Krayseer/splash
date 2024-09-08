package ru.anykeyers.authorizationserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.remote.RemoteProvider;
import ru.anykeyers.commonsapi.remote.RemoteStorageService;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    @Bean
    @ConfigurationProperties(prefix = "remote-configuration")
    public RemoteProvider remoteConfiguration() {
        return new RemoteProvider();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate,
                                                     RemoteProvider remoteProvider) {
        return new RemoteStorageService(restTemplate, remoteProvider);
    }

}

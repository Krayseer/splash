package ru.anykeyers.authorizationserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.service.RemoteStorageService;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    @Bean
    @ConfigurationProperties(prefix = "remote-configuration")
    public RemoteConfiguration remoteConfiguration() {
        return new RemoteConfiguration();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate,
                                                     RemoteConfiguration remoteConfiguration) {
        return new RemoteStorageService(restTemplate, remoteConfiguration);
    }

}

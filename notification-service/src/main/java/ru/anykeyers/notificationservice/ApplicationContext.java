package ru.anykeyers.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RemoteConfigurationService remoteConfigurationService(RestTemplate restTemplate) {
        return new RemoteConfigurationService(restTemplate);
    }

    @Bean
    public RemoteUserService remoteUserService(RestTemplate restTemplate) {
        return new RemoteUserService(restTemplate);
    }

}

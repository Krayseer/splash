package ru.anykeyers.configurationservice.context;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.commonsapi.service.RemoteStorageService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

/**
 * Контекст приложения
 */
@Configuration
public class ApplicationContext {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RemoteUserService remoteUserService(RestTemplate restTemplate) {
        return new RemoteUserService(restTemplate);
    }

    @Bean
    public RemoteConfigurationService remoteConfigurationService(RestTemplate restTemplate) {
        return new RemoteConfigurationService(restTemplate);
    }

    @Bean
    public RemoteServicesService remoteServicesService(RestTemplate restTemplate) {
        return new RemoteServicesService(restTemplate);
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate) {
        return new RemoteStorageService(restTemplate);
    }

}

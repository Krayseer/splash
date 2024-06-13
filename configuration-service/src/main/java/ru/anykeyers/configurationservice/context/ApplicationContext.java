package ru.anykeyers.configurationservice.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.commonsapi.service.RemoteStorageService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RemoteUserService remoteUserService(RestTemplate restTemplate,
                                               RemoteConfiguration remoteConfiguration) {
        return new RemoteUserService(restTemplate, remoteConfiguration);
    }

    @Bean
    public RemoteConfigurationService remoteConfigurationService(RestTemplate restTemplate,
                                                                 RemoteConfiguration remoteConfiguration) {
        return new RemoteConfigurationService(restTemplate, remoteConfiguration);
    }

    @Bean
    public RemoteServicesService remoteServicesService(RestTemplate restTemplate,
                                                       RemoteConfiguration remoteConfiguration) {
        return new RemoteServicesService(restTemplate, remoteConfiguration);
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate,
                                                     RemoteConfiguration remoteConfiguration) {
        return new RemoteStorageService(restTemplate, remoteConfiguration);
    }

}

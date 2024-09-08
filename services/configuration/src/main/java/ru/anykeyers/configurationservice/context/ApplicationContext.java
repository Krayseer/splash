package ru.anykeyers.configurationservice.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.remote.RemoteProvider;
import ru.anykeyers.commonsapi.remote.RemoteConfigurationService;
import ru.anykeyers.commonsapi.remote.RemoteServicesService;
import ru.anykeyers.commonsapi.remote.RemoteStorageService;
import ru.anykeyers.commonsapi.remote.RemoteUserService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RemoteUserService remoteUserService(RestTemplate restTemplate,
                                               RemoteProvider remoteProvider) {
        return new RemoteUserService(restTemplate, remoteProvider);
    }

    @Bean
    public RemoteConfigurationService remoteConfigurationService(RestTemplate restTemplate,
                                                                 RemoteProvider remoteProvider) {
        return new RemoteConfigurationService(restTemplate, remoteProvider);
    }

    @Bean
    public RemoteServicesService remoteServicesService(RestTemplate restTemplate,
                                                       RemoteProvider remoteProvider) {
        return new RemoteServicesService(restTemplate, remoteProvider);
    }

    @Bean
    public RemoteStorageService remoteStorageService(RestTemplate restTemplate,
                                                     RemoteProvider remoteProvider) {
        return new RemoteStorageService(restTemplate, remoteProvider);
    }

}

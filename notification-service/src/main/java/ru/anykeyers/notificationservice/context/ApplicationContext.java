package ru.anykeyers.notificationservice.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

import java.rmi.Remote;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

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
    public RemoteConfigurationService remoteConfigurationService(RestTemplate restTemplate,
                                                                 RemoteConfiguration remoteConfiguration) {
        return new RemoteConfigurationService(restTemplate, remoteConfiguration);
    }

    @Bean
    public RemoteUserService remoteUserService(RestTemplate restTemplate,
                                               RemoteConfiguration remoteConfiguration) {
        return new RemoteUserService(restTemplate, remoteConfiguration);
    }

}

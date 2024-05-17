package ru.anykeyers.orderservice.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контекст приложения
 */
@Configuration
@EnableScheduling
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

}

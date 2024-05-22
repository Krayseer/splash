package ru.anykeyers.statistics.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.service.RemoteServicesService;

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
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    @ConfigurationProperties(prefix = "remote-configuration")
    public RemoteConfiguration remoteConfiguration() {
        return new RemoteConfiguration();
    }

    @Bean
    public RemoteServicesService remoteServicesService(RestTemplate restTemplate,
                                                       RemoteConfiguration remoteConfiguration) {
        return new RemoteServicesService(restTemplate, remoteConfiguration);
    }

}

package ru.anykeyers.commonsapi.remote.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteServiceProvider implements RemoteProvider {

    private final String url;

    private final RestTemplate restTemplate;

    @Autowired
    public RemoteServiceProvider(RestTemplateBuilder restTemplateBuilder,
                                 @Value("${SERVICE_OF_SERVICES_HOST}") String host,
                                 @Value("${SERVICE_OF_SERVICES_PORT}") String port) {
        this.restTemplate = restTemplateBuilder.build();
        this.url = host + ":" + port;
    }

    @Override
    public String getBaseUrl() {
        return url + "/api/service";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

}

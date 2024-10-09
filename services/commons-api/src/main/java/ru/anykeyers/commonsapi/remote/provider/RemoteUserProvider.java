package ru.anykeyers.commonsapi.remote.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteUserProvider implements RemoteProvider {

    private final String url;

    private final RestTemplate restTemplate;

    @Autowired
    public RemoteUserProvider(RestTemplateBuilder restTemplateBuilder,
                              @Value("${USER_SERVICE_HOST}") String host,
                              @Value("${USER_SERVICE_PORT}") String port) {
        this.restTemplate = restTemplateBuilder.build();
        this.url = host + ":" + port;
    }

    @Override
    public String getBaseUrl() {
        return url + "/user";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

}

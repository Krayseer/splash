package ru.anykeyers.commonsapi.remote.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteOrderProvider implements RemoteProvider {

    private final String url;

    private final RestTemplate restTemplate;

    @Autowired
    public RemoteOrderProvider(RestTemplateBuilder restTemplateBuilder,
                               @Value("${ORDER_SERVICE_HOST}") String host,
                               @Value("${ORDER_SERVICE_PORT}") String port) {
        this.restTemplate = restTemplateBuilder.build();
        this.url = host + ":" + port;
    }

    @Override
    public String getBaseUrl() {
        return url + "/order";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

}

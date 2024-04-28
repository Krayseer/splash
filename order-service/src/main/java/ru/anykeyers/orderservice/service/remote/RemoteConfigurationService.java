package ru.anykeyers.orderservice.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.dto.ConfigurationDTO;

/**
 * Удаленный сервис конфигурации автомоек
 */
@Service
@RequiredArgsConstructor
public class RemoteConfigurationService {

    private static final String URL = "http://localhost:8052/configuration";

    private final RestTemplate restTemplate;

    /**
     * Получить информацию о конфигурации автомойки
     *
     * @param id идентификатор автомойки
     */
    public ConfigurationDTO getConfiguration(Long id) {
        String url = String.format("%s/%s", URL, id);
        return restTemplate.getForObject(url, ConfigurationDTO.class);
    }

}

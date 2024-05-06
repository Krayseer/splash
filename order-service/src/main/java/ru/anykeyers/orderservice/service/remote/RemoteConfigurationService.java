package ru.anykeyers.orderservice.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.dto.ConfigurationDTO;

import java.util.Arrays;
import java.util.List;

/**
 * Удаленный сервис конфигурации автомоек
 */
@Service
@RequiredArgsConstructor
public class RemoteConfigurationService {

    private static final String URL = "http://localhost:8052/";

    private final RestTemplate restTemplate;

    /**
     * Получить информацию о конфигурации автомойки
     *
     * @param id идентификатор автомойки
     */
    public ConfigurationDTO getConfiguration(Long id) {
        return restTemplate.getForObject(URL + "configuration/" + id, ConfigurationDTO.class);
    }

    /**
     * Получить список идентификаторов боксов для автомойки
     *
     * @param id идентификатор автомойки
     */
    public List<Long> getBoxIds(Long id) {
        Long[] ids = restTemplate.getForObject(URL + "box/" + id + "/ids", Long[].class);
        assert ids != null;
        return Arrays.stream(ids).toList();
    }
}

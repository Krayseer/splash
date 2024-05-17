package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис конфигурации автомоек
 */
@RequiredArgsConstructor
public class RemoteConfigurationService {

    private static final String URL = "http://localhost:8052/"; //TODO: В КОНСТРУКТОР

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
        return ids == null ? Collections.emptyList() : Arrays.stream(ids).toList();
    }

}

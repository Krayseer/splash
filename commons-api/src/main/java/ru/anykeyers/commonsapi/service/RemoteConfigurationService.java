package ru.anykeyers.commonsapi.service;

import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис конфигурации автомоек
 */
public class RemoteConfigurationService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteConfigurationService(RestTemplate restTemplate,
                                      RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getConfigurationServiceUrl() + "/car-wash/";
    }

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

    /**
     * Получить список работников автомойки
     *
     * @param id идентификатор автомойки
     */
    public List<UserDTO> getEmployees(Long id) {
        UserDTO[] users = restTemplate.getForObject(URL + "employee?id=" + id, UserDTO[].class);
        return users == null ? Collections.emptyList() : Arrays.stream(users).toList();
    }

}

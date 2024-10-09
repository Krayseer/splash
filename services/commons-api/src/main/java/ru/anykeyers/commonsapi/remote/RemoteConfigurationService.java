package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.configuration.BoxDTO;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.user.UserDTO;
import ru.anykeyers.commonsapi.remote.provider.RemoteConfigurationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис конфигурации автомоек
 */
@Service
@RequiredArgsConstructor
public class RemoteConfigurationService {

    private final RemoteConfigurationProvider remoteProvider;

    /**
     * Получить информацию о конфигурации автомойки
     *
     * @param id идентификатор автомойки
     */
    public ConfigurationDTO getConfiguration(Long id) {
        return remoteProvider.getRestTemplate().getForObject(
                remoteProvider.getBaseUrl() + "/configuration/" + id, ConfigurationDTO.class
        );
    }

    /**
     * Получить список идентификаторов боксов для автомойки
     *
     * @param id идентификатор автомойки
     */
    public List<Long> getBoxIds(Long id) {
        Long[] ids = remoteProvider.getRestTemplate().getForObject(
                remoteProvider.getBaseUrl() + "/box/" + id + "/ids", Long[].class
        );
        return ids == null ? Collections.emptyList() : Arrays.stream(ids).toList();
    }

    /**
     * Получить список работников автомойки
     *
     * @param id идентификатор автомойки
     */
    public List<UserDTO> getEmployees(Long id) {
        UserDTO[] users = remoteProvider.getRestTemplate().getForObject(
                remoteProvider.getBaseUrl() + "/employee/" + id, UserDTO[].class
        );
        return users == null ? new ArrayList<>() : Arrays.stream(users).toList();
    }

    public BoxDTO getBox(Long boxId) {
        return null;
    }

}

package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.user.User;
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
     * Получить список работников автомойки
     *
     * @param id идентификатор автомойки
     */
    public List<User> getEmployees(Long id) {
        User[] users = remoteProvider.getRestTemplate().getForObject(
                remoteProvider.getBaseUrl() + "/employee/" + id, User[].class
        );
        return users == null ? new ArrayList<>() : Arrays.stream(users).toList();
    }

}

package ru.anykeyers.commonsapi.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки пользователей
 */
public class RemoteUserService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteUserService(RestTemplate restTemplate,
                             RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getUserServiceUrl() + "/user";
    }

    /**
     * Получить информацию о пользователе
     *
     * @param username имя пользователя
     */
    public UserDTO getUser(String username) {
        return restTemplate.getForObject(URL + "/" + username, UserDTO.class);
    }

    /**
     * Получить информацию о пользователе
     *
     * @param id идентификатор пользователя
     */
    public UserDTO getUser(Long id) {
        String url = URL + "/id/" + id;
        return restTemplate.getForObject(url, UserDTO.class);
    }

    /**
     * Получить информацию о пользователях
     *
     * @param userIds идентификаторы пользователей
     */
    public List<UserDTO> getUsers(List<Long> userIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "/collection")
                .queryParam("user-ids", userIds.toArray())
                .encode()
                .toUriString();
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        return users == null ? Collections.emptyList() : Arrays.stream(users).toList();
    }

}

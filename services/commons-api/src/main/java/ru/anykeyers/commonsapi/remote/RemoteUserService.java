package ru.anykeyers.commonsapi.remote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.user.UserDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки пользователей
 */
@Service
public class RemoteUserService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteUserService(RestTemplate restTemplate,
                             RemoteProvider remoteProvider) {
        this.restTemplate = restTemplate;
        this.URL = remoteProvider.getUserServiceUrl() + "/auth-server/user";
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
    public List<UserDTO> getUsers(List<String> usernames) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "/collection")
                .queryParam("usernames", usernames.toArray())
                .encode()
                .toUriString();
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        return users == null ? Collections.emptyList() : Arrays.stream(users).toList();
    }

}

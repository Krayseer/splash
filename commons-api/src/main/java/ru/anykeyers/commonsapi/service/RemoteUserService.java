package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.util.List;

@RequiredArgsConstructor
public class RemoteUserService {

    private static final String URL = "http://localhost:8080/user";

    private final RestTemplate restTemplate;

    /**
     * Получить информацию о пользователе
     *
     * @param username имя пользователя
     */
    public UserDTO getUser(String username) {
        String url = URL + "/user/" + username;
        return restTemplate.getForObject(url, UserDTO.class);
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
        return null;
    }

}

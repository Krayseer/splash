package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

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
        String url = URL + "/" + username;
        return restTemplate.getForObject(url, UserDTO.class);
    }

}

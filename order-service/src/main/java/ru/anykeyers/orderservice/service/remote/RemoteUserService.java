package ru.anykeyers.orderservice.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.dto.UserDTO;

@Service
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

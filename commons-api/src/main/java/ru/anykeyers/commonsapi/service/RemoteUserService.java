package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.util.List;

/**
 * Удаленный сервис обработки пользователей
 */
@RequiredArgsConstructor
public class RemoteUserService {

    private static final String URL = "http://localhost:8080"; //TODO: В КОНСТРУКТОР

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
        String url = URL + "user/id/" + id;
        return restTemplate.getForObject(url, UserDTO.class);
    }

    public List<UserDTO> getUsers(List<Long> employeesIds) {
        return null; //TODO: РЕАЛИЗОВАТЬ
    }
}

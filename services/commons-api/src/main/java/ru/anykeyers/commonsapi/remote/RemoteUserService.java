package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.user.User;
import ru.anykeyers.commonsapi.remote.provider.RemoteUserProvider;

import java.util.List;
import java.util.UUID;

/**
 * Удаленный сервис обработки пользователей
 */
@Service
@RequiredArgsConstructor
public class RemoteUserService {

    private final RemoteUserProvider remoteUserProvider;

    /**
     * Получить информацию о пользователе
     *
     * @param username имя пользователя
     */
    public User getUser(UUID userId) {
        return remoteUserProvider.getRestTemplate()
                .getForObject(remoteUserProvider.getBaseUrl() + "/" + userId, User.class);
    }

    /**
     * Получить информацию о пользователе
     *
     * @param id идентификатор пользователя
     */
    public User getUser(Long id) {
        return remoteUserProvider.getRestTemplate()
                .getForObject(remoteUserProvider.getBaseUrl() + "/by-id/" + id, User.class);
    }

    public List<User> getUsers(List<UUID> userIds) {
        return null;
    }

//    /**
//     * Получить информацию о пользователях
//     *
//     * @param userIds идентификаторы пользователей
//     */
//    public List<UserDTO> getUsers(List<String> usernames) {
//        String url = UriComponentsBuilder
//                .fromHttpUrl(URL + "/collection")
//                .queryParam("usernames", usernames.toArray())
//                .encode()
//                .toUriString();
//        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
//        return users == null ? Collections.emptyList() : Arrays.stream(users).toList();
//    }

}

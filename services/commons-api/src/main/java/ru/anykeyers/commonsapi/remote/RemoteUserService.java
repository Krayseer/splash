package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.user.UserDTO;
import ru.anykeyers.commonsapi.remote.provider.RemoteUserProvider;

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
    public UserDTO getUser(String username) {
        return remoteUserProvider.getRestTemplate()
                .getForObject(remoteUserProvider.getBaseUrl() + "/" + username, UserDTO.class);
    }

    /**
     * Получить информацию о пользователе
     *
     * @param id идентификатор пользователя
     */
    public UserDTO getUser(Long id) {
        return remoteUserProvider.getRestTemplate()
                .getForObject(remoteUserProvider.getBaseUrl() + "/by-id/" + id, UserDTO.class);
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

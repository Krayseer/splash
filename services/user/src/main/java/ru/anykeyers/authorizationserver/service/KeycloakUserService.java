package ru.anykeyers.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.authorizationserver.config.KeycloakConfigurator;
import ru.anykeyers.authorizationserver.exception.UserNotFoundException;
import ru.anykeyers.authorizationserver.web.dto.UserRegisterRequest;
import ru.anykeyers.authorizationserver.exception.UserAlreadyExistsException;
import ru.anykeyers.authorizationserver.web.mapper.KeycloakUserMapper;
import ru.anykeyers.commonsapi.domain.user.User;
import ru.anykeyers.commonsapi.domain.user.UserInfo;
import ru.anykeyers.commonsapi.remote.RemoteStorageService;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakUserService implements UserService {

    private final KeycloakUserMapper keycloakUserMapper;

    private final KeycloakConfigurator keycloakConfigurator;

    private final RemoteStorageService remoteStorageService;

    @Override
    public User getUser(UUID id) {
        UserRepresentation user = getUserResource(id).toRepresentation();
        return keycloakUserMapper.toUser(user);
    }

    @Override
    public User getUser(String username) {
        UserRepresentation user = getUsersResource().search(username).stream()
                .filter(x -> x.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException(username));
        return keycloakUserMapper.toUser(user);
    }

    @Override
    public void addUser(UserRegisterRequest userRegisterRequest) {
        UserRepresentation user = keycloakUserMapper.toUserRepresentation(userRegisterRequest);
        Response response = getUsersResource().create(user);
        if (HttpStatus.valueOf(response.getStatus()) == HttpStatus.CONFLICT) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public void updateUser(User user, UserInfo userInfo) {
        UserResource userResource = getUserResource(user.getId());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setFirstName(userInfo.getFirstName());
        userRepresentation.setLastName(userInfo.getLastName());
        userRepresentation.setEmail(userInfo.getEmail());
        userRepresentation.setAttributes(keycloakUserMapper.toAttributes(userInfo));
        userResource.update(userRepresentation);
    }

    @Override
    public void deleteUser(UUID id) {
        getUsersResource().delete(id.toString());
    }

    @Override
    public void addPhoto(User user, MultipartFile photo) {
        ResponseEntity<String> photoUrlResponse = remoteStorageService.uploadPhoto(photo);
        if (!photoUrlResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Cannot upload photo. Storage service returned status {}", photoUrlResponse.getStatusCode());
            return;
        }
        UserInfo userInfo = user.getUserInfo();
        userInfo.setPhotoUrl(photoUrlResponse.getBody());
        UserResource userResource = getUserResource(user.getId());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.setAttributes(keycloakUserMapper.toAttributes(userInfo));
        userResource.update(userRepresentation);
    }

    private UsersResource getUsersResource() {
        return keycloakConfigurator.getUsersResource();
    }

    private UserResource getUserResource(UUID id) {
        UserResource userResource = getUsersResource().get(id.toString());
        try {
            userResource.toRepresentation();
        } catch (NotFoundException exception) {
            throw new UserNotFoundException(id);
        }
        return userResource;
    }

}

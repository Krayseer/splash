package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.commonsapi.remote.provider.RemoteStorageProvider;

/**
 * Удаленный сервис хранилища
 */
@Service
@RequiredArgsConstructor
public class RemoteStorageService {

    private final RemoteStorageProvider remoteStorageProvider;

    /**
     * Загрузить фото в хранилище
     *
     * @param photo фотография
     * @return идентификатор фотографии
     */
    @SneakyThrows
    public ResponseEntity<String> uploadPhoto(MultipartFile photo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(photo.getBytes(), headers);
        try {
            ResponseEntity<String> response = remoteStorageProvider.getRestTemplate().exchange(
                    remoteStorageProvider.getBaseUrl() + "/photo", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {}
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            }
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
            if (response.hasBody()) {
                return responseBuilder.body(response.getBody());
            }
            return responseBuilder.build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

}

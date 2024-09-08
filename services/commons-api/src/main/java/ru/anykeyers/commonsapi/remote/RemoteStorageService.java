package ru.anykeyers.commonsapi.remote;

import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * Удаленный сервис хранилища
 */
@Service
public class RemoteStorageService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteStorageService(RestTemplate restTemplate,
                                RemoteProvider remoteProvider) {
        this.restTemplate = restTemplate;
        this.URL = remoteProvider.getStorageServiceUrl() + "/storage/";
    }

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
            ResponseEntity<String> response = restTemplate.exchange(
                    URL + "photo", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {}
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

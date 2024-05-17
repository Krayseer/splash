package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис хранилища
 */
@RequiredArgsConstructor
public class RemoteStorageService {

    private static final String URL = "http://localhost:8056/photo"; //TODO: В КОНСТРУКТОР

    private final RestTemplate restTemplate;

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
                    URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {}
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

    /**
     * Загрузить список фотографий в хранилище
     *
     * @param photos список фотографий
     * @return идентификаторы фотографий
     */
    @SneakyThrows
    public ResponseEntity<List<String>> uploadPhotos(List<MultipartFile> photos) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<byte[]>> requestEntity = new HttpEntity<>(getPhotosBytes(photos), headers);
        try {
            ResponseEntity<List<String>> response = restTemplate.exchange(
                    URL + "/collection", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {}
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    private List<byte[]> getPhotosBytes(List<MultipartFile> photos) {
        return photos.stream().map(x -> {
            try {
                return x.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

}

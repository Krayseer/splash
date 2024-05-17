package ru.anykeyers.storageservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.storageservice.service.YandexPhotoService;

import java.util.List;

/**
 * REST контроллер для обработки фотографий
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {

    private final YandexPhotoService yandexPhotoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestBody byte[] photoBytes) {
        return yandexPhotoService.uploadPhoto(photoBytes);
    }

    @PostMapping("/collection")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> upload(@RequestBody List<byte[]> photoBytesCollection) {
        return yandexPhotoService.uploadPhotos(photoBytesCollection);
    }

}

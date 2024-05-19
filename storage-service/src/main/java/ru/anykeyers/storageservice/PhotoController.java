package ru.anykeyers.storageservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.storageservice.service.PhotoService;

import java.util.List;

/**
 * REST контроллер для обработки фотографий
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.PHOTO_URL)
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String upload(@RequestBody byte[] photoBytes) {
        return photoService.uploadPhoto(photoBytes);
    }

    @PostMapping("/collection")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> upload(@RequestBody List<byte[]> photoBytesCollection) {
        return photoService.uploadPhotos(photoBytesCollection);
    }

}

package ru.anykeyers.configurationservice;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.commonsapi.remote.RemoteConfigurationService;
import ru.anykeyers.commonsapi.remote.provider.RemoteStorageProvider;
import ru.anykeyers.configurationservice.domain.Configuration;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UploadPhotoTask implements Runnable {

    private final RemoteStorageProvider remoteStorageService;
    private final ConfigurationRepository configurationRepository;
    private final List<MultipartFile> photoFiles;
    private final Long configurationId;
    private final ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();

    public UploadPhotoTask(RemoteStorageProvider remoteStorageService,
                           List<MultipartFile> photoFiles,
                           ConfigurationRepository configurationRepository,
                           Long configurationId) {
        this.remoteStorageService = remoteStorageService;
        this.photoFiles = photoFiles;
        this.configurationId = configurationId;
        this.configurationRepository = configurationRepository;
    }

    @SneakyThrows
    @Override
    public void run() {
        List<String> photoUrls = new ArrayList<>();
        for (Future<ResponseEntity<String>> future : getFutures()) {
            photoUrls.add(future.get().getBody());
        }
        Configuration configuration = configurationRepository.findById(configurationId).orElseThrow(
                () -> new ConfigurationNotFoundException(configurationId)
        );
        configuration.addPhotoUrls(photoUrls);
        configurationRepository.save(configuration);
    }

    private List<Future<ResponseEntity<String>>> getFutures() {
        List<Future<ResponseEntity<String>>> futures = new ArrayList<>();
        photoFiles.forEach(photoFile -> futures.add(
                threadPool.submit(() -> remoteStorageService.uploadPhoto(photoFile))
        ));
        return futures;
    }

}

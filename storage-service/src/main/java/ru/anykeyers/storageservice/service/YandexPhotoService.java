package ru.anykeyers.storageservice.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.storageservice.YandexStorageConfig;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Сервис обработки фотографий Yandex Cloud
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class YandexPhotoService implements PhotoService {

    private final YandexStorageConfig storageConfig;

    private final ExecutorService executorService;

    @Override
    @SneakyThrows
    public String uploadPhoto(byte[] photoBytes) {
        String bucketName = storageConfig.getBucketName();
        AmazonS3 s3Client = createAmazonS3Client(storageConfig.getAccessKeyId(), storageConfig.getSecretAccessKey());
        String photoName = generateUniqueName();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(photoBytes.length);
        ByteArrayInputStream photoInputStream = new ByteArrayInputStream(photoBytes);
        s3Client.putObject(bucketName, photoName, photoInputStream, metadata);
        log.info("Upload photo: {} to bucket: {}", photoName, bucketName);
        return s3Client.getUrl(bucketName, photoName).toExternalForm();
    }

    @Override
    public List<String> uploadPhotos(List<byte[]> photoBytesCollection) {
        List<String> urls = new ArrayList<>();
        List<Future<String>> futures = new ArrayList<>();
        for (byte[] photoBytes : photoBytesCollection) {
            futures.add(executorService.submit(() -> uploadPhoto(photoBytes)));
        }
        for (Future<String> future : futures) {
            try {
                String url = future.get();
                urls.add(url);
            } catch (InterruptedException | ExecutionException e) {
                log.error("One of the thread ended with exception. Reason: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return urls;
    }

    private AmazonS3 createAmazonS3Client(String accessKeyId, String secretAccessKey) {
        try {
            return AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    "https://storage.yandexcloud.net", "ru-central1"
                            )
                    )
                    .withCredentials(
                            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey))
                    )
                    .build();
        } catch (SdkClientException e) {
            log.error("Error creating client for Object Storage via AWS SDK. Reason: {}", e.getMessage());
            throw new SdkClientException(e.getMessage());
        }
    }

    private String generateUniqueName() {
        return UUID.randomUUID().toString();
    }

}

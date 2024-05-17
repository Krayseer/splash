package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.service.RemoteStorageService;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationRegisterRequest;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationMapper;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationUpdateRequest;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.exception.UserNotFoundConfigurationException;
import ru.anykeyers.configurationservice.service.ConfigurationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса обработки конфигураций
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationMapper configurationMapper;

    private final RemoteStorageService remoteStorageService;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<ConfigurationDTO> getAllConfigurations() {
        List<Configuration> configurations = configurationRepository.findAll();
        return configurations.stream()
                .map(configurationMapper::createInfoResponse)
                .toList();
    }

    @Override
    public ConfigurationDTO getConfiguration(String username) {
        Configuration configuration = configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
        return configurationMapper.createResponse(configuration);
    }

    @Override
    public ConfigurationDTO getConfiguration(Long id) {
        Configuration configuration = configurationRepository.findById(id).orElseThrow(
                () -> new ConfigurationNotFoundException(id)
        );
        return configurationMapper.createResponse(configuration);
    }

    @Override
    public void registerConfiguration(String username, ConfigurationRegisterRequest registerRequest) {
        Configuration configuration = configurationMapper.createConfiguration(username, registerRequest);
        configurationRepository.save(configuration);
    }

    @Override
    public void updateConfiguration(String username, ConfigurationUpdateRequest configurationUpdateRequest) {
        Configuration configuration = configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
        configuration.setName(configurationUpdateRequest.getName());
        configuration.setDescription(configurationUpdateRequest.getDescription());
        configuration.setPhoneNumber(configurationUpdateRequest.getPhoneNumber());
        configuration.setAddress(configurationUpdateRequest.getAddress());
        if (configurationUpdateRequest.getPhotos() != null) {
            configuration.addPhotoUrls(uploadPhotos(configurationUpdateRequest.getPhotos()));
        }
        configurationRepository.save(configuration);
    }

    private List<String> uploadPhotos(List<MultipartFile> photos) {
        List<String> photoUrls = new ArrayList<>();
        ResponseEntity<List<String>> photoUrlsResponse = remoteStorageService.uploadPhotos(photos);
        if (photoUrlsResponse.getStatusCode().is2xxSuccessful()) {
            List<String> responseBody = photoUrlsResponse.getBody();
            if (responseBody != null) {
                photoUrls.addAll(responseBody);
            }
        } else {
            log.error("Cannot upload photos. Upload service returned status {}", photoUrlsResponse.getStatusCode());
        }
        return photoUrls;
    }

}

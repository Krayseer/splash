package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.service.RemoteStorageService;
import ru.anykeyers.configurationservice.AsyncWorker;
import ru.anykeyers.configurationservice.domain.configuration.*;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.exception.UserNotFoundConfigurationException;
import ru.anykeyers.configurationservice.service.ConfigurationService;

import java.util.List;

/**
 * Реализация сервиса обработки конфигураций
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final AsyncWorker worker;

    private final ConfigurationMapper configurationMapper;

    private final RemoteStorageService remoteStorageService;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<ConfigurationInfoDTO> getAllConfigurations() {
        List<Configuration> configurations = configurationRepository.findAll();
        return configurations.stream().map(configurationMapper::toInfoDTO).toList();
    }

    @Override
    public ConfigurationDTO getConfiguration(String username) {
        Configuration configuration = configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
        return configurationMapper.toDTO(configuration);
    }

    @Override
    public ConfigurationDTO getConfiguration(Long id) {
        Configuration configuration = configurationRepository.findById(id).orElseThrow(
                () -> new ConfigurationNotFoundException(id)
        );
        return configurationMapper.toDTO(configuration);
    }

    @Override
    public void registerConfiguration(String username, ConfigurationRegisterRequest registerRequest) {
        Configuration configuration = ConfigurationMapper.createConfiguration(username, registerRequest);
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
        configuration.setLongitude(configurationUpdateRequest.getLongitude());
        configuration.setLatitude(configurationUpdateRequest.getLatitude());
        configuration.setOpenTime(configurationUpdateRequest.getOpenTime());
        configuration.setCloseTime(configurationUpdateRequest.getCloseTime());
        configuration.setManagementProcessOrders(configurationUpdateRequest.isManagementProcessOrders());
        configurationRepository.save(configuration);
        if (configurationUpdateRequest.getPhotos() != null) {
            uploadPhotos(configuration.getId(), configurationUpdateRequest.getPhotos());
        }
        log.info("Update configuration: {}", configuration);
    }

    @Override
    public void deleteConfiguration(String username) {
        Configuration configuration = configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
        configurationRepository.delete(configuration);
        log.info("Deleted configuration: {}", configuration);
    }

    private void uploadPhotos(Long configurationId, List<MultipartFile> photos) {
        worker.addTask(() -> {
            ResponseEntity<List<String>> photoUrlsResponse = remoteStorageService.uploadPhotos(photos);
            Configuration configuration = configurationRepository.findById(configurationId).orElseThrow(
                    () -> new ConfigurationNotFoundException(configurationId)
            );
            configuration.addPhotoUrls(photoUrlsResponse.getBody());
            configurationRepository.save(configuration);
        });
    }

}

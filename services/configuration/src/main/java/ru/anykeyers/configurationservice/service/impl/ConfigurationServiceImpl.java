package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.remote.RemoteStorageService;
import ru.anykeyers.configurationservice.TaskManager;
import ru.anykeyers.configurationservice.UploadPhotoTask;
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

    private final TaskManager taskManager;
    private final RemoteStorageService remoteStorageService;
    private final ConfigurationRepository configurationRepository;

    @Override
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    @Override
    public Configuration getConfiguration(String username) {
        return configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
    }

    @Override
    public Configuration getConfiguration(Long id) {
        return configurationRepository.findById(id).orElseThrow(() -> new ConfigurationNotFoundException(id));
    }

    @Override
    public void registerConfiguration(String username, ConfigurationRegisterRequest registerRequest) {
        Configuration configuration = ConfigurationMapper.toConfiguration(username, registerRequest);
        configurationRepository.save(configuration);
    }

    @Override
    public void updateConfiguration(String username, ConfigurationUpdateRequest configurationUpdateRequest) {
        Configuration configuration = getConfiguration(username);
        configuration.setName(configurationUpdateRequest.getName());
        configuration.setDescription(configurationUpdateRequest.getDescription());
        configuration.setPhoneNumber(configurationUpdateRequest.getPhoneNumber());
        configuration.setAddress(configurationUpdateRequest.getAddress());
        configuration.setLongitude(configurationUpdateRequest.getLongitude());
        configuration.setLatitude(configurationUpdateRequest.getLatitude());
        configuration.setOpenTime(configurationUpdateRequest.getOpenTime());
        configuration.setCloseTime(configurationUpdateRequest.getCloseTime());
        configuration.setManagementProcessOrders(configurationUpdateRequest.isManagementProcessOrders());
        configuration.setSelfService(configuration.isSelfService());
        configurationRepository.save(configuration);
        if (configurationUpdateRequest.getPhotos() != null) {
            taskManager.addTask(new UploadPhotoTask(remoteStorageService, configurationUpdateRequest.getPhotos(), configurationRepository, configuration.getId()));
        }
        log.info("Update configuration: {}", configuration);
    }

    @Override
    public void deleteConfiguration(String username) {
        Configuration configuration = getConfiguration(username);
        configurationRepository.delete(configuration);
        log.info("Deleted configuration: {}", configuration);
    }

}

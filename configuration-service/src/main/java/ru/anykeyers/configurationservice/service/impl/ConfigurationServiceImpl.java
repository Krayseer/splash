package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationRegisterRequest;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationMapper;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationUpdateRequest;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.exception.UserNotFoundConfigurationException;
import ru.anykeyers.configurationservice.service.ConfigurationService;

import java.util.List;

/**
 * Реализация сервиса обработки конфигураций
 */
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final ConfigurationMapper configurationMapper;

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
        configurationRepository.save(configuration);
    }

}

package ru.anykeyers.configurationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.ConfigurationFactory;
import ru.anykeyers.configurationservice.ConfigurationRepository;
import ru.anykeyers.configurationservice.domain.Configuration;
import ru.anykeyers.configurationservice.domain.ConfigurationRequest;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.exception.UserNotFoundConfigurationException;

import java.util.List;

/**
 * Реализация сервиса обработки конфигураций
 */
@Service
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final ConfigurationFactory configurationFactory;

    @Override
    public List<ConfigurationDTO> getAllConfigurations() {
        List<Configuration> configurations = configurationRepository.findAll();
        return configurations.stream()
                .map(configurationFactory::createInfoResponse)
                .toList();
    }

    @Override
    public ConfigurationDTO getConfiguration(String username) {
        Configuration configuration = configurationRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundConfigurationException(username)
        );
        return configurationFactory.createResponse(configuration);
    }

    @Override
    public ConfigurationDTO getConfiguration(Long id) {
        Configuration configuration = configurationRepository.findById(id).orElseThrow(
                () -> new ConfigurationNotFoundException(id)
        );
        return configurationFactory.createResponse(configuration);
    }

    @Override
    public void saveConfiguration(String username, ConfigurationRequest configurationRequest) {
        Configuration configuration = configurationFactory.createConfiguration(username, configurationRequest);
        configurationRepository.save(configuration);
    }

}

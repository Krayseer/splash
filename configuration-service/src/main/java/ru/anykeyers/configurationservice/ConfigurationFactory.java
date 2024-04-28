package ru.anykeyers.configurationservice;

import ru.anykeyers.commonsapi.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.Configuration;
import ru.anykeyers.configurationservice.domain.ConfigurationRequest;

/**
 * Фабрика по созданию конфигруации
 */
public final class ConfigurationFactory {

    /**
     * Создать конфигурацию на основе данных из запроса
     *
     * @param configurationRequest запрос с данными конфигурации
     */
    public static Configuration createConfiguration(String username, ConfigurationRequest configurationRequest) {
        return Configuration.builder()
                .username(username)
                .tin(configurationRequest.getTin())
                .typeOrg(configurationRequest.getTypeOrg())
                .email(configurationRequest.getEmail())
                .name(configurationRequest.getName())
                .description(configurationRequest.getDescription())
                .phoneNumber(configurationRequest.getPhoneNumber())
                .address(configurationRequest.getAddress())
                .build();
    }

    /**
     * Создать данные для передачи информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public static ConfigurationDTO createResponse(Configuration configuration) {
        return ConfigurationDTO.builder()
                .id(configuration.getId())
                .username(configuration.getUsername())
                .tin(configuration.getTin())
                .typeOrg(configuration.getTypeOrg())
                .email(configuration.getEmail())
                .name(configuration.getName())
                .description(configuration.getDescription())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .createdAt(configuration.getCreatedAt())
                .build();
    }

    /**
     * Создать данные только из общей информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public static ConfigurationDTO createInfoResponse(Configuration configuration) {
        return ConfigurationDTO.builder()
                .name(configuration.getName())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .description(configuration.getDescription())
                .build();
    }

}

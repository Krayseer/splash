package ru.anykeyers.configurationservice.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.configurationservice.domain.entity.Configuration;
import ru.anykeyers.configurationservice.domain.ConfigurationRequest;
import ru.anykeyers.configurationservice.service.RemoteServicesService;

import java.util.List;

/**
 * Фабрика по созданию конфигруации
 */
@Component
@RequiredArgsConstructor
public class ConfigurationFactory {

    private final RemoteServicesService remoteServicesService;

    /**
     * Создать конфигурацию на основе данных из запроса
     *
     * @param configurationRequest запрос с данными конфигурации
     */
    public Configuration createConfiguration(String username, ConfigurationRequest configurationRequest) {
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
    public ConfigurationDTO createResponse(Configuration configuration) {
        List<ServiceDTO> services = remoteServicesService.getServices(configuration.getId());
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
                .services(services)
                .boxes(BoxFactory.createResponse(configuration.getBoxes()))
                .createdAt(configuration.getCreatedAt().toString())
                .build();
    }

    /**
     * Создать данные только из общей информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public ConfigurationDTO createInfoResponse(Configuration configuration) {
        List<ServiceDTO> services = remoteServicesService.getServices(configuration.getId());
        return ConfigurationDTO.builder()
                .id(configuration.getId())
                .name(configuration.getName())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .description(configuration.getDescription())
                .services(services)
                .boxes(BoxFactory.createResponse(configuration.getBoxes()))
                .build();
    }

}

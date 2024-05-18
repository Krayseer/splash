package ru.anykeyers.configurationservice.domain.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.configurationservice.domain.box.BoxMapper;

import java.time.Instant;
import java.util.List;

/**
 * Фабрика по созданию конфигруации
 */
@Component
@RequiredArgsConstructor
public class ConfigurationMapper {

    private final RemoteServicesService remoteServicesService;

    /**
     * Создать конфигурацию на основе данных из запроса
     *
     * @param registerRequest запрос с данными конфигурации
     */
    public Configuration createConfiguration(String username, ConfigurationRegisterRequest registerRequest) {
        return Configuration.builder()
                .username(username)
                .tin(registerRequest.getTin())
                .typeOrganization(registerRequest.getTypeOrganization())
                .email(registerRequest.getEmail())
                .createdAt(Instant.now())
                .build();
    }

    /**
     * Создать данные для передачи информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public ConfigurationDTO createDTO(Configuration configuration) {
        List<ServiceDTO> services = remoteServicesService.getServices(configuration.getId());
        return ConfigurationDTO.builder()
                .id(configuration.getId())
                .username(configuration.getUsername())
                .tin(configuration.getTin())
                .typeOrganization(configuration.getTypeOrganization().name())
                .email(configuration.getEmail())
                .name(configuration.getName())
                .description(configuration.getDescription())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .openTime(configuration.getOpenTime())
                .closeTime(configuration.getCloseTime())
                .services(services)
                .boxes(BoxMapper.createResponse(configuration.getBoxes()))
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
                .boxes(BoxMapper.createResponse(configuration.getBoxes()))
                .build();
    }

}

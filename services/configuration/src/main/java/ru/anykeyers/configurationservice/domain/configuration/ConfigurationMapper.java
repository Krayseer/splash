package ru.anykeyers.configurationservice.domain.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.service.ServiceDTO;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationSimpleDTO;
import ru.anykeyers.commonsapi.remote.RemoteServicesService;
import ru.anykeyers.commonsapi.remote.RemoteUserService;
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
    private final RemoteUserService remoteUserService;

    /**
     * Создать конфигурацию на основе данных из запроса
     *
     * @param registerRequest запрос с данными конфигурации
     */
    public static Configuration toConfiguration(String username, ConfigurationRegisterRequest registerRequest) {
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
    public ConfigurationDTO toDTO(Configuration configuration) {
        List<ServiceDTO> services = remoteServicesService.getServices(configuration.getId());
        return ConfigurationDTO.builder()
                .id(configuration.getId())
                .user(remoteUserService.getUser(configuration.getUsername()))
                .organizationInfo(configuration.get)
                .typeOrganization(configuration.getTypeOrganization().name())
                .email(configuration.getEmail())
                .name(configuration.getName())
                .description(configuration.getDescription())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .longitude(configuration.getLongitude())
                .latitude(configuration.getLatitude())
                .openTime(configuration.getOpenTime())
                .closeTime(configuration.getCloseTime())
                .services(services)
                .boxes(BoxMapper.createDTO(configuration.getBoxes()))
                .photoUrls(configuration.getPhotoUrls())
                .managementProcessOrders(configuration.isManagementProcessOrders())
                .selfService(configuration.isSelfService())
                .createdAt(configuration.getCreatedAt().toString())
                .build();
    }

    /**
     * Создать данные для передачи информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public static ConfigurationSimpleDTO toSimpleDTO(Configuration configuration) {
        return ConfigurationSimpleDTO.builder()
                .id(configuration.getId())
                .name(configuration.getName())
                .build();
    }

    /**
     * Создать данные только из общей информации об автомойке
     *
     * @param configuration конфигурация автомойки
     */
    public ConfigurationInfoDTO toInfoDTO(Configuration configuration) {
        List<ServiceDTO> services = remoteServicesService.getServices(configuration.getId());
        return ConfigurationInfoDTO.builder()
                .id(configuration.getId())
                .name(configuration.getName())
                .phoneNumber(configuration.getPhoneNumber())
                .address(configuration.getAddress())
                .longitude(configuration.getLongitude())
                .latitude(configuration.getLatitude())
                .description(configuration.getDescription())
                .services(services)
                .boxes(BoxMapper.createDTO(configuration.getBoxes()))
                .photoUrls(configuration.getPhotoUrls())
                .build();
    }

}

package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationRegisterRequest;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationUpdateRequest;
import ru.anykeyers.configurationservice.service.ConfigurationService;

import java.util.List;

/**
 * REST контроллер для обработки конфигураций
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.CONFIGURATION_NAME)
@Tag(name = "Обработка конфигураций автомоек")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Operation(summary = "Получить конфигурацию автомойки авторизованного пользователя")
    @GetMapping
    public ConfigurationDTO getConfiguration(@AuthenticationPrincipal Jwt jwt) {
        return configurationService.getConfiguration(jwt.getSubject());
    }

    @Operation(summary = "Получить конфигурацию автомойки по идентификатору")
    @GetMapping("/{id}")
    public ConfigurationDTO getConfigurationById(@PathVariable Long id) {
        return configurationService.getConfiguration(id);
    }

    @Operation(summary = "Получить список всех автомоек")
    @GetMapping("/all")
    public List<ConfigurationDTO> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }

    @Operation(summary = "Сохранить конфигурацию автомойки")
    @PostMapping
    public void saveConfiguration(@AuthenticationPrincipal Jwt jwt,
                                  @RequestBody ConfigurationRegisterRequest registerRequest) {
        configurationService.registerConfiguration(jwt.getSubject(), registerRequest);
    }

    @Operation(summary = "Обновить конфигурацию автомойки")
    @PutMapping
    public void updateConfiguration(@AuthenticationPrincipal Jwt jwt,
                                    @RequestBody ConfigurationUpdateRequest configurationUpdateRequest) {
        configurationService.updateConfiguration(jwt.getSubject(), configurationUpdateRequest);
    }

    @Operation(summary = "Удалить конфигурацию автомойки")
    @DeleteMapping
    public void deleteConfiguration(@AuthenticationPrincipal Jwt jwt) {
        configurationService.deleteConfiguration(jwt.getSubject());
    }

}

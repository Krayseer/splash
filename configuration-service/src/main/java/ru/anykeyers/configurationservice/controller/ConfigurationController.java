package ru.anykeyers.configurationservice.controller;

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
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    public ConfigurationDTO getConfiguration(@AuthenticationPrincipal Jwt jwt) {
        return configurationService.getConfiguration(jwt.getSubject());
    }

    @GetMapping("/{id}")
    public ConfigurationDTO getConfigurationById(@PathVariable Long id) {
        return configurationService.getConfiguration(id);
    }

    @GetMapping("/all")
    public List<ConfigurationDTO> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }

    @PostMapping
    public void saveConfiguration(@AuthenticationPrincipal Jwt jwt,
                                  @RequestBody ConfigurationRegisterRequest registerRequest) {
        configurationService.registerConfiguration(jwt.getSubject(), registerRequest);
    }

    @PutMapping
    public void updateConfiguration(@AuthenticationPrincipal Jwt jwt,
                                    @RequestBody ConfigurationUpdateRequest configurationUpdateRequest) {
        configurationService.updateConfiguration(jwt.getSubject(), configurationUpdateRequest);
    }

}

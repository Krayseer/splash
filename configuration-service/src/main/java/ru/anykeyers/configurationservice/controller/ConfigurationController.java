package ru.anykeyers.configurationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.ConfigurationRequest;
import ru.anykeyers.configurationservice.service.ConfigurationService;

import java.util.List;

/**
 * REST контроллер для обработки конфигураций
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration")
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
                                  @RequestBody ConfigurationRequest configurationRequest) {
        configurationService.saveConfiguration(jwt.getSubject(), configurationRequest);
    }

}

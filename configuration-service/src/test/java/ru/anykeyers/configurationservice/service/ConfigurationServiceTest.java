package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.configurationservice.domain.TypeOrganization;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationRegisterRequest;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationUpdateRequest;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.service.impl.ConfigurationServiceImpl;

import java.util.Optional;

/**
 * Тесты для {@link ConfigurationService}
 */
@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    /**
     * Тест успешной регистрации автомойки
     */
    @Test
    void registerConfiguration() {
        String username = "test-user";
        ConfigurationRegisterRequest request = new ConfigurationRegisterRequest(
                "124751045", TypeOrganization.OOO, "123@email.com"
        );
        configurationService.registerConfiguration(username, request);
        ArgumentCaptor<Configuration> captor = ArgumentCaptor.forClass(Configuration.class);
        Mockito.verify(configurationRepository, Mockito.times(1)).save(captor.capture());
        Configuration savedConfiguration = captor.getValue();

        Assertions.assertEquals("test-user", savedConfiguration.getUsername());
        Assertions.assertEquals("124751045", savedConfiguration.getTin());
        Assertions.assertEquals(TypeOrganization.OOO, savedConfiguration.getTypeOrganization());
        Assertions.assertEquals("123@email.com", savedConfiguration.getEmail());
    }

    /**
     * Тест обновления существующей конфигурации
     */
    @Test
    void updateConfiguration() {
        Configuration existsConfiguration = Configuration.builder()
                .username("test-user")
                .name("Best carwash")
                .description("My car wash is really best")
                .phoneNumber("+799999999")
                .address("Lvov")
                .openTime("08:00")
                .closeTime("23:00")
                .managementProcessOrders(false)
                .build();
        ConfigurationUpdateRequest request = new ConfigurationUpdateRequest(
                "BestIce",
                "My car wash is really best",
                "+795214545",
                "Lvov",
                "05:00",
                "23:00",
                null,
                true
        );
        Mockito.when(configurationRepository.findByUsername("test-user")).thenReturn(Optional.ofNullable(existsConfiguration));

        configurationService.updateConfiguration("test-user", request);

        ArgumentCaptor<Configuration> captor = ArgumentCaptor.forClass(Configuration.class);
        Mockito.verify(configurationRepository, Mockito.times(1)).save(captor.capture());
        Configuration savedConfiguration = captor.getValue();

        Assertions.assertEquals("test-user", savedConfiguration.getUsername());
        Assertions.assertEquals("BestIce", savedConfiguration.getName());
        Assertions.assertEquals("My car wash is really best", savedConfiguration.getDescription());
        Assertions.assertEquals("+795214545", savedConfiguration.getPhoneNumber());
        Assertions.assertEquals("Lvov", savedConfiguration.getAddress());
        Assertions.assertEquals("05:00", savedConfiguration.getOpenTime());
        Assertions.assertEquals("23:00", savedConfiguration.getCloseTime());
        Assertions.assertTrue(savedConfiguration.isManagementProcessOrders());
    }

}
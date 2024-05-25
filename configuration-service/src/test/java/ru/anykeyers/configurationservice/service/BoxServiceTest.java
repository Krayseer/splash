package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.repository.BoxRepository;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.service.impl.BoxServiceImpl;

import java.util.Optional;

/**
 * Тесты для {@link BoxService}
 */
@ExtendWith(MockitoExtension.class)
class BoxServiceTest {

    @Mock
    private BoxRepository boxRepository;

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private BoxServiceImpl boxService;

    /**
     * Тест добавления бокса несуществующей автомойке
     */
    @Test
    void addBoxWithNotExistsCarWash() {
        Long carWashId = 1L;
        Mockito.when(configurationRepository.findById(carWashId)).thenReturn(Optional.empty());
        ConfigurationNotFoundException exception = Assertions.assertThrows(
                ConfigurationNotFoundException.class, () -> boxService.addBox(carWashId, null)
        );
        Assertions.assertEquals("Configuration with id 1 not found", exception.getMessage());
    }

    @Test
    void addBox() {
        BoxRequest request = new BoxRequest("First box super");
        Long carWashId = 1L;
        Configuration configuration = Configuration.builder().id(carWashId).build();
        Mockito.when(configurationRepository.findById(carWashId)).thenReturn(Optional.of(configuration));
        boxService.addBox(carWashId, request);
        Mockito.verify(boxRepository, Mockito.times(1)).save(Mockito.any());
    }

}
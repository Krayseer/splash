package ru.anykeyers.configurationservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.configurationservice.domain.box.Box;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.exception.BoxNotFoundException;
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

    @Captor
    private ArgumentCaptor<Box> captor;

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

    /**
     * Тест добавления бокса
     */
    @Test
    void addBox() {
        BoxRequest request = new BoxRequest("First box super");
        Long carWashId = 1L;
        Configuration configuration = Configuration.builder().id(carWashId).build();
        Mockito.when(configurationRepository.findById(carWashId)).thenReturn(Optional.of(configuration));
        boxService.addBox(carWashId, request);
        Mockito.verify(boxRepository, Mockito.times(1)).save(Mockito.any());
    }

    /**
     * Тест обновления несуществующего бокса
     */
    @Test
    void updateNotExistsBox() {
        Mockito.when(boxRepository.findById(1L)).thenReturn(Optional.empty());
        BoxNotFoundException exception = Assertions.assertThrows(
                BoxNotFoundException.class, () -> boxService.updateBox(1L, new BoxRequest())
        );
        Assertions.assertEquals("Box not found: 1", exception.getMessage());
    }

    /**
     * Тест обновления бокса
     */
    @Test
    void updateBox() {
        BoxRequest request = new BoxRequest("Updated name");
        Box box = Box.builder().id(1L).name("Name").build();
        Mockito.when(boxRepository.findById(1L)).thenReturn(Optional.of(box));

        boxService.updateBox(1L, request);

        Mockito.verify(boxRepository, Mockito.times(1)).save(captor.capture());
        Box capturedBox = captor.getValue();
        Assertions.assertEquals(1L, capturedBox.getId());
        Assertions.assertEquals("Updated name", capturedBox.getName());
    }

    /**
     * Тест удаления бокса
     */
    @Test
    void deleteBox() {
        boxService.deleteBox(1L);
        Mockito.verify(boxRepository, Mockito.times(1)).deleteById(1L);
    }

}
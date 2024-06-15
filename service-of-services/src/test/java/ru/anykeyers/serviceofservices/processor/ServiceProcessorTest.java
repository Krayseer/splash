package ru.anykeyers.serviceofservices.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.serviceofservices.ServiceRepository;
import ru.anykeyers.serviceofservices.domain.ServiceEntity;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.serviceofservices.exception.ServiceNotFoundException;

import java.util.Optional;

/**
 * Тесты для {@link ServiceProcessor}
 */
@ExtendWith(MockitoExtension.class)
class ServiceProcessorTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceProcessorImpl serviceProcessor;

    @Captor
    private ArgumentCaptor<ServiceEntity> captor;

    /**
     * Тест сохранения услуги
     */
    @Test
    void saveService() {
        ServiceRequest request = new ServiceRequest("test-service", 3600, 250);
        serviceProcessor.saveService(1L, request);
        Mockito.verify(serviceRepository).save(captor.capture());
        ServiceEntity entity = captor.getValue();
        Assertions.assertEquals(1L, entity.getCarWashId());
        Assertions.assertEquals("test-service", entity.getName());
        Assertions.assertEquals(3600, entity.getDuration());
        Assertions.assertEquals(250, entity.getPrice());
    }

    /**
     * Тест обновления несуществующей услуги
     */
    @Test
    void updateNotFoundService() {
        Mockito.when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        ServiceNotFoundException exception = Assertions.assertThrows(
                ServiceNotFoundException.class, () -> serviceProcessor.updateService(1L, new ServiceRequest())
        );
        Assertions.assertEquals("Service not found: 1", exception.getMessage());
        Mockito.verify(serviceRepository, Mockito.never()).save(Mockito.any());
    }

    /**
     * Тест обновления услуги
     */
    @Test
    void updateService() {
        ServiceEntity service = ServiceEntity.builder()
                .id(1L)
                .carWashId(1L)
                .name("test-name")
                .duration(100)
                .price(200)
                .build();
        Mockito.when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        ServiceRequest request = new ServiceRequest("updated-name", 3600, 250);
        serviceProcessor.updateService(1L, request);
        Mockito.verify(serviceRepository).save(captor.capture());
        ServiceEntity entity = captor.getValue();
        Assertions.assertEquals(1L, entity.getCarWashId());
        Assertions.assertEquals("updated-name", entity.getName());
        Assertions.assertEquals(3600, entity.getDuration());
        Assertions.assertEquals(250, entity.getPrice());
    }

    /**
     * Тест удаления услуги
     */
    @Test
    void deleteService() {
        serviceProcessor.deleteService(1L);
        Mockito.verify(serviceRepository).deleteById(1L);
    }

}
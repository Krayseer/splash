package ru.anykeyers.serviceofservices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.serviceofservices.processor.ServiceProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * REST контроллер для обработки услуг
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
@Tag(name = "Обработка услуг")
public class ServiceController {

    private final ServiceProcessor serviceProcessor;

    @Operation(summary = "Получить список услуг")
    @GetMapping("/list")
    public List<ServiceDTO> getServices(
            @Parameter(name = "Идентификаторы услуг") @RequestParam("service-ids") Long[] serviceIds
    ) {
        return serviceProcessor.getServices(Arrays.asList(serviceIds));
    }

    @Operation(summary = "Получить продолжительность выполнения услуг")
    @GetMapping("/duration")
    public long getServicesDuration(
            @Parameter(name = "Идентификаторы услуг") @RequestParam("service-ids") Long[] serviceIds) {
        return serviceProcessor.getServicesDuration(Arrays.asList(serviceIds));
    }

    @Operation(summary = "Получить все услуги автомойки")
    @GetMapping("/{carWashId}")
    public List<ServiceDTO> getAllServices(
            @Parameter(name = "Идентификатор автомойки") @PathVariable Long carWashId
    ) {
        return serviceProcessor.getAllServices(carWashId);
    }

    @Operation(summary = "Сохранить услугу")
    @PostMapping("/{carWashId}")
    public void saveService(
            @Parameter(name = "Идентификатор автомойки") @PathVariable Long carWashId,
            @Parameter(name = "Данные об услуге") @RequestBody ServiceRequest serviceRequest) {
        serviceProcessor.saveService(carWashId, serviceRequest);
    }

    @Operation(summary = "Обновить услугу")
    @PutMapping("/{serviceId}")
    public void updateService(
            @Parameter(name = "Идентификатор услуги") @PathVariable Long serviceId,
            @Parameter(name = "Обновленные данные об услуге") @RequestBody ServiceRequest serviceRequest
    ) {
       serviceProcessor.updateService(serviceId, serviceRequest);
    }

    @Operation(summary = "Удалить услугу")
    @DeleteMapping("/{serviceId}")
    public void deleteService(
            @Parameter(name = "Идентификатор услуги") @PathVariable Long serviceId
    ) {
        serviceProcessor.deleteService(serviceId);
    }

}

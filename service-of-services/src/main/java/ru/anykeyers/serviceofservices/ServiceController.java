package ru.anykeyers.serviceofservices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.dto.ServiceDTO;
import ru.anykeyers.serviceofservices.service.ServiceProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * REST контроллер для обработки услуг
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private final ServiceProcessor serviceProcessor;

    @GetMapping
    public List<ServiceDTO> getServices(@RequestParam("ids") String serviceIds) {
        return serviceProcessor.getServices(
                Arrays.stream(serviceIds.split(",")).map(Long::parseLong).toList()
        );
    }

    @GetMapping("/{carWashId}")
    public List<ServiceDTO> getAllServices(@PathVariable Long carWashId) {
        return serviceProcessor.getAllServices(carWashId);
    }

    @PostMapping("/{carWashId}")
    public void saveService(@PathVariable Long carWashId,
                            @RequestBody ServiceRequest serviceRequest) {
        serviceProcessor.saveService(carWashId, serviceRequest);
    }

}
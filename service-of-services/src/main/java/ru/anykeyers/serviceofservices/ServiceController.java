package ru.anykeyers.serviceofservices;

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
public class ServiceController {

    private final ServiceProcessor serviceProcessor;

    @GetMapping
    public List<ServiceDTO> getServices(@RequestParam("service-ids") Long[] serviceIds) {
        return serviceProcessor.getServices(Arrays.asList(serviceIds));
    }

    @GetMapping("/duration")
    public long getServicesDuration(@RequestParam("service-ids") Long[] serviceIds) {
        return serviceProcessor.getServicesDuration(Arrays.asList(serviceIds));
    }

    @GetMapping("/{carWashId}")
    public List<ServiceDTO> getAllServices(@PathVariable Long carWashId) {
        return serviceProcessor.getAllServices(carWashId);
    }

    @PostMapping("/{carWashId}")
    public void saveService(@PathVariable Long carWashId, @RequestBody ServiceRequest serviceRequest) {
        serviceProcessor.saveService(carWashId, serviceRequest);
    }

}

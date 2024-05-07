package ru.anykeyers.serviceofservices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.serviceofservices.domain.ServiceRequest;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.serviceofservices.processor.ServiceProcessor;

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
        return serviceProcessor.getServices(Mapper.parseFromString(serviceIds));
    }

    @GetMapping("/duration")
    public long getServicesDuration(@RequestParam("ids") String serviceIds) {
        return serviceProcessor.getServicesDuration(Mapper.parseFromString(serviceIds));
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

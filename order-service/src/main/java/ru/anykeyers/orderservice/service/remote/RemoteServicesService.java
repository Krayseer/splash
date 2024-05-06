package ru.anykeyers.orderservice.service.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.dto.ServiceDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Удаленный сервис обработки услуг
 */
@Service
@RequiredArgsConstructor
public class RemoteServicesService {

    private static final String URL = "http://localhost:8053/service";

    private final RestTemplate restTemplate;

    /**
     * Получить список с данными об услугах
     *
     * @param serviceIds идентификаторы услуг
     */
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        ServiceDTO[] services = restTemplate.getForObject(
                URL + "?ids=" + serviceIds.stream().map(String::valueOf).collect(Collectors.joining(",")),
                ServiceDTO[].class
        );
        if (services == null) {
            throw new RuntimeException("Services not found");
        }
        return Arrays.stream(services).toList();
    }

    /**
     * Получить период выполнения услуг
     *
     * @param serviceIds идентификаторы услуг
     */
    public long getServicesDuration(List<Long> serviceIds) {
        String servicesString = serviceIds.stream().map(Object::toString).collect(Collectors.joining(", "));
        Long duration = restTemplate.getForObject(URL + "/duration" + "?ids=" + servicesString, Long.class);
        if (duration == null) {
            throw new RuntimeException("Services not found");
        }
        return duration;
    }
}

package ru.anykeyers.configurationservice.service;

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
     * @param carWashId идентификатор автомойки
     */
    public List<ServiceDTO> getServices(Long carWashId) {
        ServiceDTO[] services = restTemplate.getForObject(URL + "/" + carWashId, ServiceDTO[].class);
        if (services == null) {
            throw new RuntimeException("Services not found");
        }
        return Arrays.stream(services).toList();
    }

}

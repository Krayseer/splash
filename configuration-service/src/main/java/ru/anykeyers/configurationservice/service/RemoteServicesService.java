package ru.anykeyers.configurationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;
import ru.anykeyers.configurationservice.exception.ServiceNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        try {
            ServiceDTO[] services = restTemplate.getForObject(URL + "/" + carWashId, ServiceDTO[].class);
            if (services == null) {
                throw new ServiceNotFoundException(carWashId);
            }
            return Arrays.stream(services).toList();
        } catch (RestClientException e) {
            return Collections.emptyList();
        }
    }

}

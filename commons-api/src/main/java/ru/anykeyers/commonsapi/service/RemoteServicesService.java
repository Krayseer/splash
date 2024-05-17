package ru.anykeyers.commonsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.dto.ServiceDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки услуг
 */
@RequiredArgsConstructor
public class RemoteServicesService {

    private static final String URL = "http://localhost:8053/service"; //TODO: В КОНСТРУКТОР

    private final RestTemplate restTemplate;

    /**
     * Получить список услуг автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    public List<ServiceDTO> getServices(Long carWashId) {
        ServiceDTO[] services = restTemplate.getForObject(URL + "/" + carWashId, ServiceDTO[].class);
        return services == null ? Collections.emptyList() : Arrays.stream(services).toList();
    }

    /**
     * Получить список с данными об услугах
     *
     * @param serviceIds идентификаторы услуг
     */
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam("service-ids", serviceIds.toArray())
                .encode()
                .toUriString();
        ServiceDTO[] services = restTemplate.getForObject(url, ServiceDTO[].class);
        return services == null ? Collections.emptyList() : Arrays.stream(services).toList();
    }

    /**
     * Получить период выполнения услуг
     *
     * @param serviceIds идентификаторы услуг
     */
    public long getServicesDuration(List<Long> serviceIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "/duration")
                .queryParam("service-ids", serviceIds.toArray())
                .encode()
                .toUriString();
        Long duration = restTemplate.getForObject(url, Long.class);
        return duration == null ? 0 : duration;
    }

}

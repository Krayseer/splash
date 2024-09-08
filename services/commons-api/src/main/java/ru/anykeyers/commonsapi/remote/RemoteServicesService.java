package ru.anykeyers.commonsapi.remote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.service.ServiceDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки услуг
 */
@Service
public class RemoteServicesService {

    private final RestTemplate restTemplate;

    private final RemoteProvider remoteProvider;

    public RemoteServicesService(RestTemplate restTemplate, RemoteProvider remoteProvider) {
        this.restTemplate = restTemplate;
        this.remoteProvider = remoteProvider;
    }

    /**
     * Получить список услуг автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    public List<ServiceDTO> getServices(Long carWashId) {
        ServiceDTO[] services = restTemplate.getForObject(getBaseUrl() + "/car-wash/" + carWashId, ServiceDTO[].class);
        return services == null ? Collections.emptyList() : Arrays.stream(services).toList();
    }

    /**
     * Получить список с данными об услугах
     *
     * @param serviceIds идентификаторы услуг
     */
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(getBaseUrl() + "/list")
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
                .fromHttpUrl(getBaseUrl() + "/duration")
                .queryParam("service-ids", serviceIds.toArray())
                .encode()
                .toUriString();
        Long duration = restTemplate.getForObject(url, Long.class);
        return duration == null ? 0 : duration;
    }

    private String getBaseUrl() {
        return remoteProvider.getServiceOfServicesUrl() + "/service";
    }

}

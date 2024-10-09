package ru.anykeyers.commonsapi.remote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.service.ServiceDTO;
import ru.anykeyers.commonsapi.remote.provider.RemoteProvider;
import ru.anykeyers.commonsapi.remote.provider.RemoteServiceProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки услуг
 */
@Service
public class RemoteServicesService {

    private final RemoteServiceProvider remoteServiceProvider;

    public RemoteServicesService(RemoteServiceProvider remoteServiceProvider) {
        this.remoteServiceProvider = remoteServiceProvider;
    }

    /**
     * Получить список услуг автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    public List<ServiceDTO> getServices(Long carWashId) {
        ServiceDTO[] services = remoteServiceProvider.getRestTemplate()
                .getForObject(remoteServiceProvider.getBaseUrl() + "/car-wash/" + carWashId, ServiceDTO[].class);
        return services == null ? Collections.emptyList() : Arrays.stream(services).toList();
    }

    /**
     * Получить список с данными об услугах
     *
     * @param serviceIds идентификаторы услуг
     */
    public List<ServiceDTO> getServices(List<Long> serviceIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(remoteServiceProvider.getBaseUrl() + "/list")
                .queryParam("service-ids", serviceIds.toArray())
                .encode()
                .toUriString();
        ServiceDTO[] services = remoteServiceProvider.getRestTemplate().getForObject(url, ServiceDTO[].class);
        return services == null ? Collections.emptyList() : Arrays.stream(services).toList();
    }

    /**
     * Получить период выполнения услуг
     *
     * @param serviceIds идентификаторы услуг
     */
    public long getServicesDuration(List<Long> serviceIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(remoteServiceProvider.getBaseUrl() + "/duration")
                .queryParam("service-ids", serviceIds.toArray())
                .encode()
                .toUriString();
        Long duration = remoteServiceProvider.getRestTemplate().getForObject(url, Long.class);
        return duration == null ? 0 : duration;
    }

}

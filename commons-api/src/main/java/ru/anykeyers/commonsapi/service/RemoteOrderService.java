package ru.anykeyers.commonsapi.service;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки заказов
 */
public class RemoteOrderService {

    private final String URL;

    private final RestTemplate restTemplate;

    public RemoteOrderService(RestTemplate restTemplate,

                              RemoteConfiguration remoteConfiguration) {
        this.restTemplate = restTemplate;
        this.URL = remoteConfiguration.getOrderServiceUrl() + "/order/";
    }

    /**
     * Получить список заказов
     *
     * @param carWashId идентификатор автомойки
     * @param date      дата
     */
    public List<OrderDTO> getOrders(Long carWashId, String date) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "car-wash/by-date")
                .queryParam("carWashId", carWashId)
                .queryParam("date", date)
                .encode()
                .toUriString();
        OrderDTO[] orders = restTemplate.getForObject(url, OrderDTO[].class);
        return orders == null ? Collections.emptyList() : Arrays.stream(orders).toList();

    }

}

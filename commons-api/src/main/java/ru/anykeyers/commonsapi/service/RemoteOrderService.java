package ru.anykeyers.commonsapi.service;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.RemoteConfiguration;
import ru.anykeyers.commonsapi.domain.dto.FullOrderDTO;
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
    public List<FullOrderDTO> getOrders(Long carWashId, String date) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "car-wash/by-date")
                .queryParam("carWashId", carWashId)
                .queryParam("date", date)
                .encode()
                .toUriString();
        FullOrderDTO[] orders = restTemplate.getForObject(url, FullOrderDTO[].class);
        return orders == null ? Collections.emptyList() : Arrays.stream(orders).toList();
    }

    /**
     * Получить список заказов
     *
     * @param orderIds идентификаторы заказов
     */
    public List<OrderDTO> getOrders(List<Long> orderIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(URL + "list")
                .queryParam("order-ids", orderIds.toArray())
                .encode()
                .toUriString();
        OrderDTO[] orders = restTemplate.getForObject(url, OrderDTO[].class);
        return orders == null ? Collections.emptyList() : Arrays.stream(orders).toList();
    }

}

package ru.anykeyers.commonsapi.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.anykeyers.commonsapi.domain.order.OrderDTO;
import ru.anykeyers.commonsapi.remote.provider.RemoteOrderProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Удаленный сервис обработки заказов
 */
@Service
@RequiredArgsConstructor
public class RemoteOrderService {

    private final RemoteOrderProvider remoteProvider;

    /**
     * Получить список заказов
     *
     * @param carWashId идентификатор автомойки
     * @param date      дата
     */
    public List<OrderDTO> getOrders(Long carWashId, String date) {
        String url = UriComponentsBuilder
                .fromHttpUrl(remoteProvider.getBaseUrl() + "/by-date")
                .queryParam("carWashId", carWashId)
                .queryParam("date", date)
                .encode()
                .toUriString();
        OrderDTO[] orders = remoteProvider.getRestTemplate().getForObject(url, OrderDTO[].class);
        return orders == null ? Collections.emptyList() : Arrays.stream(orders).toList();
    }

    public OrderDTO getOrder(Long orderId) {
        return null;
    }

    /**
     * Получить список заказов
     *
     * @param orderIds идентификаторы заказов
     */
    public List<OrderDTO> getOrders(List<Long> orderIds) {
        String url = UriComponentsBuilder
                .fromHttpUrl(remoteProvider.getBaseUrl() + "/list")
                .queryParam("order-ids", orderIds.toArray())
                .encode()
                .toUriString();
        OrderDTO[] orders = remoteProvider.getRestTemplate().getForObject(url, OrderDTO[].class);
        return orders == null ? Collections.emptyList() : Arrays.stream(orders).toList();
    }

}

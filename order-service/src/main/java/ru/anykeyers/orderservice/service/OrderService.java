package ru.anykeyers.orderservice.service;

import ru.anykeyers.commonsapi.domain.dto.OrderDTO;

/**
 * Сервис обработки заказов
 */
public interface OrderService {

    /**
     * Получить информацию о заказе
     *
     * @param id идентификатор заказа
     */
    OrderDTO getOrder(Long id);

}

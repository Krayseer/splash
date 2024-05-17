package ru.anykeyers.orderservice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.orderservice.domain.order.Order;
import ru.anykeyers.commonsapi.domain.OrderState;

import java.util.List;

/**
 * DAO для работы с заказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    /**
     * Получить список заказов по статусу
     *
     * @param orderState     статус заказа
     * @param pageable  настройка пагинации
     */
    List<Order> findByStatus(OrderState orderState, Pageable pageable);

    /**
     * Получить список заказов пользователя по статусу
     *
     * @param username  имя пользователя
     * @param status    статус заказа
     */
    List<Order> findByUsernameAndStatus(String username, OrderState status);

    /**
     * Получить список заказов пользователя по списку статусов
     *
     * @param username  имя пользователя
     * @param orderStates    список состояний
     */
    List<Order> findByUsernameAndStatusIn(String username, List<OrderState> orderStates);

    /**
     * Получить список заказов по идентификаторам боксов
     *
     * @param boxIds идентификаторы боксов
     */
    List<Order> findByBoxIdIn(List<Long> boxIds);

    /**
     * Получить количество заказов у автомойки
     *
     * @param carWashId идентификатор автомойки
     * @param status    статус заказа
     */
    int countByCarWashIdAndStatus(Long carWashId, OrderState status);
}

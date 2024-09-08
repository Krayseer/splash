package ru.anykeyers.orderservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.commonsapi.domain.order.OrderState;

import java.util.List;

/**
 * DAO для работы с заказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    /**
     * Получить список заказов по статусу
     *
     * @param orderState    статус заказа
     * @param pageable      настройка пагинации
     */
    List<Order> findByState(OrderState orderState, Pageable pageable);

    /**
     * Получить список заказов пользователя по статусу
     *
     * @param username  имя пользователя
     * @param status    статус заказа
     */
    List<Order> findByUsernameAndState(String username, OrderState status);

    /**
     * Получить список заказов пользователя по списку статусов
     *
     * @param username      имя пользователя
     * @param orderStates   список состояний
     */
    List<Order> findByUsernameAndStateIn(String username, List<OrderState> orderStates);

    /**
     * Получить список заказов по идентификаторам боксов
     *
     * @param boxIds идентификаторы боксов
     */
    List<Order> findByBoxIdIn(List<Long> boxIds);

    /**
     * Получить список заказов у автомойки
     *
     * @param carWashId     идентификатор автомойки
     * @param orderState    статус заказа
     */
    List<Order> findByCarWashIdAndState(Long carWashId, OrderState orderState);

    /**
     * Получить количество заказов у автомойки
     *
     * @param carWashId идентификатор автомойки
     * @param status    статус заказа
     */
    int countByCarWashIdAndState(Long carWashId, OrderState status);

    /**
     * Получить список заказов по идентификатору автомойки и списку статусов
     *
     * @param carWashId     идентификатор автомойки
     * @param waitConfirm   список состояний
     */
    List<Order> findByCarWashIdAndStateIn(Long carWashId, List<OrderState> waitConfirm);

    /**
     * Удалить заказ
     *
     * @param username  имя пользователя
     * @param orderId   идентификатор заказа
     */
    void deleteByUsernameAndId(String username, Long orderId);

}

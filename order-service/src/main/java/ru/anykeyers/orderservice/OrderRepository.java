package ru.anykeyers.orderservice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.orderservice.domain.Order;
import ru.anykeyers.commonsapi.domain.State;

import java.util.List;

/**
 * DAO для работы с заказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    /**
     * Получить список заказов по статусу
     *
     * @param state     статус заказа
     * @param pageable  настройка пагинации
     */
    List<Order> findByStatus(State state, Pageable pageable);

    /**
     * Получить список заказов пользователя по статусу
     *
     * @param username  имя пользователя
     * @param status    статус заказа
     */
    List<Order> findByUsernameAndStatus(String username, State status);

    /**
     * Получить список заказов пользователя по списку статусов
     *
     * @param username  имя пользователя
     * @param states    список состояний
     */
    List<Order> findByUsernameAndStatusIn(String username, List<State> states);

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
    int countByCarWashIdAndStatus(Long carWashId, State status);
}

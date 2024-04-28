package ru.anykeyers.orderservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.orderservice.domain.Order;

import java.util.Optional;

/**
 * DAO для работы с заказами
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Получить заказ
     *
     * @param username имя пользователя
     */
    Optional<Order> findByUsername(String username);

}

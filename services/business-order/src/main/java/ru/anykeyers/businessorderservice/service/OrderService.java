package ru.anykeyers.businessorderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.anykeyers.businessorderservice.repository.BusinessOrderRepository;
import ru.anykeyers.businessorderservice.domain.BusinessOrder;
import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.order.OrderDTO;
import ru.anykeyers.commonsapi.domain.user.UserDTO;
import ru.anykeyers.commonsapi.remote.RemoteConfigurationService;
import ru.anykeyers.commonsapi.remote.RemoteOrderService;
import ru.anykeyers.commonsapi.remote.RemoteUserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final RemoteUserService remoteUserService;

    private final RemoteOrderService remoteOrderService;

    private final RemoteConfigurationService remoteConfigurationService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final BusinessOrderRepository businessOrderRepository;

    /**
     * Получить список всех заказов работника
     */
    public List<OrderDTO> getOrders(String username) {
        List<BusinessOrder> businessOrders = businessOrderRepository.findByEmployeeUsername(username);
        return remoteOrderService.getOrders(businessOrders.stream().map(BusinessOrder::getOrderId).toList());
    }

    /**
     * Получить свободных работников на заказ
     *
     * @param orderId идентификатор заказа
     */
    public List<UserDTO> getFreeEmployees(Long orderId) {
        OrderDTO order = remoteOrderService.getOrder(orderId);
        return remoteUserService.getUsers(getFreeEmployees(order));
    }

    /**
     * Назначить работника на заказ
     *
     * @param orderId          идентификатор заказа
     * @param employeeUsername имя пользователя работника
     */
    public void appointOrderEmployee(Long orderId, String employeeUsername) {
        BusinessOrder businessOrder = BusinessOrder.builder()
                .orderId(orderId)
                .employeeUsername(employeeUsername)
                .build();
        businessOrderRepository.save(businessOrder);
        log.info("Appoint order employee '{}' for order '{}'", employeeUsername, orderId);
    }

    /**
     * Удалить работника с заказа
     *
     * @param businessOrderId идентификатор заказа
     */
    public void disappointEmployeeFromOrder(Long businessOrderId) {
        BusinessOrder order = businessOrderRepository.findById(businessOrderId).orElseThrow(
                () -> new RuntimeException("Order not found")
        );
        kafkaTemplate.send(MessageQueue.ORDER_EMPLOYEE_DISAPPOINT, order.getOrderId());
        businessOrderRepository.deleteById(businessOrderId);
    }

    /**
     * Получить свободных работников на заказ
     *
     * @param order заказ
     */
    public List<String> getFreeEmployees(OrderDTO order) {
        List<String> employees = remoteConfigurationService.getEmployees(order.getCarWashId()).stream()
                .map(UserDTO::username)
                .collect(Collectors.toList());
        List<OrderDTO> orders = remoteOrderService.getOrders(order.getCarWashId(), DateUtils.toDate(order.getStartTime()));
        if (CollectionUtils.isEmpty(orders)) {
            return employees;
        }
        employees.removeAll(getBusyEmployees(orders, order));
        return employees;
    }

    private List<String> getBusyEmployees(List<OrderDTO> savedOrders, OrderDTO newOrder) {
        return savedOrders.stream()
                .filter(savedOrder -> isOverlapOrders(savedOrder, newOrder))
                .map(OrderDTO::getId)
                .map(businessOrderRepository::findByOrderId)
                .map(BusinessOrder::getEmployeeUsername)
                .toList();
    }

    /**
     * Пересекаются ли заказы
     *
     * @param savedOrder    сохраненный заказ
     * @param currentOrder  текущий заказ
     */
    private boolean isOverlapOrders(OrderDTO savedOrder, OrderDTO currentOrder) {
        Instant savedStart = savedOrder.getStartTime();
        Instant currentStart = currentOrder.getStartTime();
        Instant savedEnd = savedOrder.getEndTime();
        Instant currentEnd = currentOrder.getEndTime();
        return currentStart.isBefore(savedEnd) && currentEnd.isAfter(savedStart) && currentEnd.isBefore(savedEnd) ||
                currentStart.isAfter(savedStart) && currentEnd.isBefore(savedEnd) ||
                currentEnd.isAfter(savedEnd) && currentStart.isAfter(savedStart) && currentStart.isBefore(savedEnd);
    }

}

package ru.anykeyers.businessorderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.anykeyers.businessorderservice.BusinessOrderRepository;
import ru.anykeyers.businessorderservice.domain.BusinessOrder;
import ru.anykeyers.commonsapi.DateUtils;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteOrderService;
import ru.anykeyers.commonsapi.service.RemoteUserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final EventService eventService;

    private final RemoteUserService remoteUserService;

    private final RemoteOrderService remoteOrderService;

    private final BusinessOrderRepository businessOrderRepository;

    private final RemoteConfigurationService remoteConfigurationService;

    /**
     * Получить список всех заказов работника
     */
    public List<OrderDTO> getOrders(String username) {
        UserDTO user = remoteUserService.getUser(username);
        List<BusinessOrder> orders = businessOrderRepository.findByEmployeeId(user.getId());
        return null; //TODO доделать
    }

    /**
     * Обработать создание заказа
     *
     * @param order данные о новом заказе
     */
    public void processOrderCreate(OrderDTO order) {
        ConfigurationDTO configuration = remoteConfigurationService.getConfiguration(order.getCarWashId());
        if (configuration == null || configuration.isManagementProcessOrders()) {
            // Не обрабатываем заказ, если у автомойки на это назначен менеджер
            return;
        }
        List<Long> employeeIds = new ArrayList<>(remoteConfigurationService.getEmployees(order.getCarWashId()).stream()
                .map(UserDTO::getId)
                .toList());
        String orderDate = DateUtils.toDate(Instant.parse(order.getStartTime()));
        List<OrderDTO> orders = remoteOrderService.getOrders(configuration.getId(), orderDate);
        if (CollectionUtils.isEmpty(orders)) {
            appointOrderEmployee(order.getId(), employeeIds.getFirst());
            return;
        }
        orders.stream()
                .filter(o -> isOverlapOrders(o, order))
                .map(OrderDTO::getId)
                .map(businessOrderRepository::findByOrderId)
                .map(BusinessOrder::getEmployeeId)
                .forEach(employeeIds::remove);
        if (employeeIds.isEmpty()) {;
            eventService.sendOrderRemoveEvent(order.getId());
            return;
        }
        appointOrderEmployee(order.getId(), employeeIds.getFirst());
    }

    /**
     * Назначить работника заказу
     *
     * @param orderId       идентификатор заказа
     * @param employeeId    идентификатор работника
     */
    public void appointOrderEmployee(Long orderId, Long employeeId) {
        BusinessOrder businessOrder = new BusinessOrder(orderId, employeeId);
        businessOrderRepository.save(businessOrder);
        log.info("Appoint order employee {} for order {}", employeeId, orderId);
        eventService.sendOrderApplyEmployeeEvent(String.valueOf(orderId));
    }

    /**
     * Пересекаются ли заказы
     *
     * @param savedOrder    сохраненный заказ
     * @param currentOrder  текущий заказ
     */
    private boolean isOverlapOrders(OrderDTO savedOrder, OrderDTO currentOrder) {
        Instant savedStart = Instant.parse(savedOrder.getStartTime());
        Instant currentStart = Instant.parse(currentOrder.getStartTime());
        Instant savedEnd = Instant.parse(savedOrder.getEndTime());
        Instant currentEnd = Instant.parse(currentOrder.getEndTime());
        return currentStart.isBefore(savedEnd) && currentEnd.isAfter(savedStart) && currentEnd.isBefore(savedEnd) ||
                currentStart.isAfter(savedStart) && currentEnd.isBefore(savedEnd) ||
                currentEnd.isAfter(savedEnd) && currentStart.isAfter(savedStart) && currentStart.isBefore(savedEnd);
    }

}

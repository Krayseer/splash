package ru.anykeyers.orderservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.commonsapi.domain.OrderState;
import ru.anykeyers.commonsapi.domain.PaymentType;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.service.RemoteServicesService;
import ru.anykeyers.orderservice.OrderRepository;
import ru.anykeyers.orderservice.domain.order.Order;
import ru.anykeyers.orderservice.domain.order.OrderRequest;
import ru.anykeyers.orderservice.domain.time.TimeRange;
import ru.anykeyers.orderservice.exception.BoxFreeNotFoundException;
import ru.anykeyers.orderservice.exception.OrderNotFoundException;
import ru.anykeyers.orderservice.service.impl.OrderServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Тесты для {@link OrderService}
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private BoxService boxService;

    @Mock
    private EventService eventService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RemoteServicesService remoteServicesService;

    @InjectMocks
    private OrderServiceImpl orderService;

    /**
     * Тест успешного создания заказа
     */
    @Test
    void saveOrder() {
        String username = "test-user";
        Long carWashId = 1L;
        List<Long> serviceIds = List.of(1L, 2L);
        Instant startTime = Instant.now();
        OrderRequest orderRequest = new OrderRequest(carWashId, serviceIds, startTime, PaymentType.SBP);
        long mockDuration = 2000L;
        Instant endTime = startTime.plusMillis(mockDuration);
        Long boxId = 1L;
        Order expectedOrder = Order.builder()
                .id(1L)
                .username(username)
                .carWashId(carWashId)
                .serviceIds(serviceIds)
                .startTime(startTime)
                .endTime(endTime)
                .boxId(boxId)
                .typePayment(PaymentType.SBP)
                .status(OrderState.WAIT_CONFIRM)
                .createdAt(Instant.now())
                .build();
        Mockito.when(remoteServicesService.getServicesDuration(serviceIds)).thenReturn(mockDuration);
        Mockito.when(boxService.getBoxForOrder(carWashId, new TimeRange(startTime, endTime))).thenReturn(Optional.of(boxId));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(expectedOrder);
        OrderDTO actualOrderDTO = orderService.saveOrder(username, orderRequest);
        OrderDTO expectedOrderDTO = OrderDTO.builder()
                .id(1L)
                .username(username)
                .carWashId(carWashId)
                .boxId(boxId)
                .status(OrderState.WAIT_CONFIRM)
                .startTime(startTime.toString())
                .serviceIds(serviceIds)
                .endTime(endTime.toString())
                .typePayment(PaymentType.SBP)
                .createdAt(actualOrderDTO.getCreatedAt())
                .build();
        Assertions.assertEquals(expectedOrderDTO, actualOrderDTO);
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
        Mockito.verify(eventService, Mockito.times(1)).sendOrderCreatedEvent(expectedOrderDTO);
    }

    /**
     * Тест сохранения заказа при не имеющихся свободных боксах
     */
    @Test
    void saveOrderWithNotFoundFreeBox() {
        OrderRequest orderRequest = new OrderRequest(1L, List.of(1L, 2L), Instant.now(), PaymentType.SBP);
        Mockito.when(boxService.getBoxForOrder(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        BoxFreeNotFoundException exception = Assertions.assertThrows(
                BoxFreeNotFoundException.class, () -> orderService.saveOrder("test", orderRequest)
        );
        Assertions.assertEquals("Free box not found", exception.getMessage());
    }

    /**
     * Тест обработки назначения работника заказу
     */
    @Test
    void applyOrderEmployee() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderState.WAIT_CONFIRM);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        orderService.applyOrderEmployee(1L);
        Assertions.assertEquals(OrderState.WAIT_PROCESS, order.getStatus());
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }

    /**
     * Тест обработки назначения работника несуществующему заказу
     */
    @Test
    void applyEmployeeOnNotExistsOrder() {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        OrderNotFoundException exception = Assertions.assertThrows(
                OrderNotFoundException.class, () -> orderService.applyOrderEmployee(1L)
        );
        Assertions.assertEquals("Order with id 1 not found", exception.getMessage());
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any());
    }

}
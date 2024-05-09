package ru.anykeyers.businessorderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.anykeyers.businessorderservice.BusinessOrderRepository;
import ru.anykeyers.businessorderservice.domain.BusinessOrder;
import ru.anykeyers.businessorderservice.domain.BusinessOrderRequest;
import ru.anykeyers.commonsapi.MessageQueue;
import ru.anykeyers.commonsapi.domain.dto.OrderDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RemoteUserService remoteUserService;

    private final BusinessOrderRepository businessOrderRepository;

    @Override
    public List<OrderDTO> getOrders(String username) {
        UserDTO user = remoteUserService.getUser(username);
        List<BusinessOrder> orders = businessOrderRepository.findByEmployeeId(user.getId());
        return null; //TODO доделать
    }

    @Override
    public void processOrder(BusinessOrderRequest request) {
        BusinessOrder businessOrder = BusinessOrder.builder()
                .orderId(request.getOrderId())
                .employeeId(request.getEmployeeId())
                .build();
        businessOrderRepository.save(businessOrder);
        kafkaTemplate.send(MessageQueue.ORDER_EMPLOYEE_APPLY, String.valueOf(request.getOrderId()));
    }

}

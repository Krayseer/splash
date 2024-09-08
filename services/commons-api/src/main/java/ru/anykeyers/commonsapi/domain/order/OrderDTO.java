package ru.anykeyers.commonsapi.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.PaymentType;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String username;
    private Long carWashId;
    private Long boxId;
    private OrderState state;
    private List<Long> serviceIds;
    private PaymentType paymentType;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
}

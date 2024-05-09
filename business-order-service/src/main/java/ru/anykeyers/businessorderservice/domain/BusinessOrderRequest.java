package ru.anykeyers.businessorderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOrderRequest {

    private Long orderId;

    private Long employeeId;

}

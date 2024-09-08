package ru.anykeyers.commonsapi.domain.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.Address;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDTO {
    private Long id;
    private String username;
    private OrganizationInfo organizationInfo;
    private Address address;
    private List<Long> serviceIds;
    private List<BoxDTO> boxes;
    private List<String> photoUrls;
    private Instant openTime;
    private Instant closeTime;
    private OrderProcessMode orderProcessMode;
}
package ru.anykeyers.commonsapi.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.configuration.ConfigurationDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO{
    private String username;
    private ConfigurationDTO configuration;
}
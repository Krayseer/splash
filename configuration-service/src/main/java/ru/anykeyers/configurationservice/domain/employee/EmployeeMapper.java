package ru.anykeyers.configurationservice.domain.employee;

import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;

/**
 * Маппер для работников
 */
public class EmployeeMapper {

    /**
     * Создать DTO с данными о работнике
     *
     * @param employee работник
     */
    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getUserId(), employee.getConfiguration().getId());
    }

}

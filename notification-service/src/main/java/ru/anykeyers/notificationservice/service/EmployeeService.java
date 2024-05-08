package ru.anykeyers.notificationservice.service;

import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;

/**
 * Сервис обработки работников
 */
public interface EmployeeService {

    void notifyEmployeeApply(EmployeeDTO employee);

}

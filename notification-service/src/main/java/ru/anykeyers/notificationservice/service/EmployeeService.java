package ru.anykeyers.notificationservice.service;

import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;

/**
 * Сервис обработки работников
 */
public interface EmployeeService {

    /**
     * Создать уведомление о принятии работником пришглашения на работу
     *
     * @param employee работник
     */
    void notifyEmployeeApply(EmployeeDTO employee);

}

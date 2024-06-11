package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.UserDTO;

import java.util.List;

/**
 * Сервис обработки работников автомойки
 */
public interface EmployeeService {

    /**
     * Получить список работников автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    List<UserDTO> getCarWashEmployees(Long carWashId);

    /**
     * Добавить работника автомойке
     *
     * @param carWashId идентификатор автомойки
     * @param userId    идентификатор пользователя
     */
    void addCarWashEmployee(Long carWashId, Long userId);

    /**
     * Удалить работника с автомойки
     *
     * @param carWashId идентификатор автомойки
     * @param userId    идентификатор работника
     */
    void deleteEmployee(Long carWashId, Long userId);

}

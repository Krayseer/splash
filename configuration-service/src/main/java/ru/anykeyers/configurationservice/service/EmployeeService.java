package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.user.UserDTO;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;

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
     * @param configuration автомойка
     * @param userId        идентификатор пользователя
     */
    void addCarWashEmployee(Configuration configuration, Long userId);

    /**
     * Удалить работника с автомойки
     *
     * @param carWashId идентификатор автомойки
     * @param userId    идентификатор работника
     */
    void deleteEmployee(Long carWashId, Long userId);

}

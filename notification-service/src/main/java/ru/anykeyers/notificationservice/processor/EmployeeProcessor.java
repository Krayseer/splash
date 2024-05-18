package ru.anykeyers.notificationservice.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.notificationservice.domain.Notification;
import ru.anykeyers.notificationservice.service.NotificationServiceCompound;

/**
 * Обработчик сообщений по работникам
 */
@Service
@RequiredArgsConstructor
public class EmployeeProcessor {

    private final RemoteUserService remoteUserService;

    private final RemoteConfigurationService remoteConfigurationService;

    private final NotificationServiceCompound notificationServiceCompound;

    /**
     * Создать уведомление о принятии работником пришглашения на работу
     *
     * @param employee работник
     */
    public void processEmployeeApply(EmployeeDTO employee) {
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(employee.getCarWashId());
        UserDTO employeeUser = remoteUserService.getUser(employee.getUserId());
        UserDTO carWashOwner = remoteUserService.getUser(configurationDTO.getUsername());
        notifyOwner(carWashOwner, employeeUser);
        notifyEmployee(employeeUser, configurationDTO);
    }

    /**
     * Уведомить хозяина автомойки
     *
     * @param owner     хозяин автомойки
     * @param employee  работник
     */
    private void notifyOwner(UserDTO owner, UserDTO employee) {
        String message = String.format("""
                        У вас новый работник!
                        Имя: %s
                        Фамилия: %s
                        Роли: %s
                        """, employee.getName(), employee.getUsername(), employee.getRoles());
        Notification notification = new Notification("Новый работник", message);
        notificationServiceCompound.notify(owner, notification);
    }

    /**
     * Уведомить работника
     *
     * @param employee      работник
     * @param configuration автомойка
     */
    private void notifyEmployee(UserDTO employee, ConfigurationDTO configuration) {
        String message = String.format("""
                        Поздравляем, вы стали работником автомойки!
                        Автомойка: %s
                        Адрес: %s
                        """, configuration.getName(), configuration.getAddress());
        Notification notification = new Notification("Вы приняты на работу", message);
        notificationServiceCompound.notify(employee, notification);
    }

}

package ru.anykeyers.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.commonsapi.domain.dto.EmployeeDTO;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.commonsapi.service.RemoteConfigurationService;
import ru.anykeyers.commonsapi.service.RemoteUserService;
import ru.anykeyers.notificationservice.domain.EmailAddress;
import ru.anykeyers.notificationservice.domain.EmailContent;
import ru.anykeyers.notificationservice.service.EmailService;
import ru.anykeyers.notificationservice.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmailService emailService;

    private final RemoteUserService remoteUserService;

    private final RemoteConfigurationService remoteConfigurationService;

    @Override
    public void notifyEmployeeApply(EmployeeDTO employee) {
        UserDTO employeeUser = remoteUserService.getUser(employee.getUserId());
        ConfigurationDTO configurationDTO = remoteConfigurationService.getConfiguration(employee.getCarWashId());
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
        EmailContent<String> content = new EmailContent<>(owner.getEmail(),
                String.format("""
                        У вас новый работник!
                        Имя: %s
                        Фамилия: %s
                        Роли: %s
                        """, employee.getName(), employee.getUsername(), employee.getRoles())
        );
        emailService.sendMessage(new EmailAddress(owner.getEmail()), content);
    }

    /**
     * Уведомить работника
     *
     * @param employee      работник
     * @param configuration автомойка
     */
    private void notifyEmployee(UserDTO employee, ConfigurationDTO configuration) {
        EmailContent<String> content = new EmailContent<>(employee.getEmail(),
                String.format("""
                        Поздравляем, вы стали работником автомойки!
                        Автомойка: %s
                        Адрес: %s
                        """, configuration.getName(), configuration.getAddress())
        );
        emailService.sendMessage(new EmailAddress(employee.getEmail()), content);
    }

}

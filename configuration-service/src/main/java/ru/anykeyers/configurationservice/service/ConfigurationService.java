package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.ConfigurationRequest;

import java.util.List;

/**
 * Сервис обработки конфигурации автомойки
 */
public interface ConfigurationService {

    /**
     * Получить информацию обо всех автомойках
     */
    List<ConfigurationDTO> getAllConfigurations();

    /**
     * Получить информацию об автомойке
     *
     * @param username имя пользователя хозяина автомойки
     */
    ConfigurationDTO getConfiguration(String username);

    /**
     * Получить информацию об автомойке
     *
     * @param id идентификатор автомойки
     */
    ConfigurationDTO getConfiguration(Long id);

    /**
     * Сохранить автомойку
     *
     * @param username              имя пользователя хозяина автомойки
     * @param configurationRequest  данные для создания автомойки
     */
    void saveConfiguration(String username, ConfigurationRequest configurationRequest);

}

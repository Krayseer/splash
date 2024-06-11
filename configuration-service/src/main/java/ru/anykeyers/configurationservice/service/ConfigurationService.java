package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.ConfigurationDTO;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationRegisterRequest;
import ru.anykeyers.configurationservice.domain.configuration.ConfigurationUpdateRequest;

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
     * Зарегистрировать автомойку
     *
     * @param username          имя пользователя хозяина автомойки
     * @param registerRequest   данные для регистрации автомойки
     */
    void registerConfiguration(String username, ConfigurationRegisterRequest registerRequest);

    /**
     * Обновить данные об автомойке
     *
     * @param username                      имя пользователя хозяина автомойки
     * @param configurationUpdateRequest    данные об автомойке
     */
    void updateConfiguration(String username, ConfigurationUpdateRequest configurationUpdateRequest);

    /**
     * Удалить конфигурацию автомойки
     *
     * @param username имя пользователя хозяина автомойки
     */
    void deleteConfiguration(String username);

}

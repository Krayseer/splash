package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.configuration.BoxDTO;
import ru.anykeyers.configurationservice.web.dto.BoxRequest;

import java.util.List;

/**
 * Сервис для обработки боксов
 */
public interface BoxService {

    /**
     * Получить список боксов для автомойки
     *
     * @param carWashId идентификатор автоомойки
     */
    List<BoxDTO> getCarWashBoxes(Long carWashId);

    /**
     * Получить список идентификаторов боксов автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    List<Long> getCarWashBoxesIds(Long carWashId);

    /**
     * Добавить бокс автомойке
     *
     * @param carWashId     идентификатор автомойки
     * @param boxRequest    запрос с данными о боксе
     */
    void addBox(Long carWashId, BoxRequest boxRequest);

    /**
     * Обновить информацию о боксе
     *
     * @param boxId         идентификатор бокса
     * @param boxRequest    обновленные данные о боксе
     */
    void updateBox(Long boxId, BoxRequest boxRequest);

    /**
     * Удалить бокс
     *
     * @param boxId идентификатор бокса
     */
    void deleteBox(Long boxId);

}

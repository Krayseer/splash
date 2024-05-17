package ru.anykeyers.configurationservice.service;

import ru.anykeyers.commonsapi.domain.dto.BoxDTO;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;

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
    BoxDTO addBox(Long carWashId, BoxRequest boxRequest);

}

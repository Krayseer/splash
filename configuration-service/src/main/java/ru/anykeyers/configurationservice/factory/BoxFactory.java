package ru.anykeyers.configurationservice.factory;

import ru.anykeyers.commonsapi.domain.dto.BoxDTO;
import ru.anykeyers.configurationservice.domain.dto.BoxRequest;
import ru.anykeyers.configurationservice.domain.entity.Box;
import ru.anykeyers.configurationservice.domain.entity.Configuration;

import java.util.List;

/**
 * Фабрика по созданию боксов
 */
public final class BoxFactory {

    /**
     * Создать бокс
     *
     * @param configuration автомойка
     * @param boxRequest    данные о боксе
     */
    public static Box createBox(Configuration configuration, BoxRequest boxRequest) {
        return Box.builder()
                .name(boxRequest.getName())
                .configuration(configuration)
                .build();
    }

    /**
     * Создать ответ со списком данных о боксах
     *
     * @param boxes список боксов
     */
    public static List<BoxDTO> createResponse(List<Box> boxes) {
        return boxes.stream().map(BoxFactory::createResponse).toList();
    }

    /**
     * Создать ответ с данными о боксе
     *
     * @param box бокс
     */
    public static BoxDTO createResponse(Box box) {
        return new BoxDTO(box.getId(), box.getName(), box.getConfiguration().getId());
    }

}

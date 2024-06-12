package ru.anykeyers.configurationservice.domain.box;

import ru.anykeyers.commonsapi.domain.dto.configuration.BoxDTO;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;

import java.util.List;

/**
 * Фабрика по созданию боксов
 */
public final class BoxMapper {

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
    public static List<BoxDTO> createDTO(List<Box> boxes) {
        return boxes.stream().map(BoxMapper::createDTO).toList();
    }

    /**
     * Создать ответ с данными о боксе
     *
     * @param box бокс
     */
    public static BoxDTO createDTO(Box box) {
        return new BoxDTO(box.getId(), box.getName(), box.getConfiguration().getId());
    }

}

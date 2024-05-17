package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.BoxDTO;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.domain.box.Box;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.domain.box.BoxMapper;
import ru.anykeyers.configurationservice.repository.BoxRepository;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.service.BoxService;

import java.util.List;

/**
 * Реализация сервиса обработки боксов
 */
@Service
@RequiredArgsConstructor
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;

    private final ConfigurationRepository configurationRepository;

    @Override
    public List<BoxDTO> getCarWashBoxes(Long carWashId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        List<Box> boxes = boxRepository.findByConfiguration(configuration);
        return BoxMapper.createResponse(boxes);
    }

    @Override
    public List<Long> getCarWashBoxesIds(Long carWashId) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        List<Box> boxes = boxRepository.findByConfiguration(configuration);
        return boxes.stream().map(Box::getId).toList();
    }

    @Override
    public BoxDTO addBox(Long carWashId, BoxRequest boxRequest) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        Box box = boxRepository.save(BoxMapper.createBox(configuration, boxRequest));
        return BoxMapper.createResponse(box);
    }

}

package ru.anykeyers.configurationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anykeyers.commonsapi.domain.dto.configuration.BoxDTO;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.domain.box.Box;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;
import ru.anykeyers.configurationservice.exception.BoxNotFoundException;
import ru.anykeyers.configurationservice.exception.ConfigurationNotFoundException;
import ru.anykeyers.configurationservice.domain.box.BoxMapper;
import ru.anykeyers.configurationservice.repository.BoxRepository;
import ru.anykeyers.configurationservice.repository.ConfigurationRepository;
import ru.anykeyers.configurationservice.service.BoxService;

import java.util.List;

/**
 * Реализация сервиса обработки боксов
 */
@Slf4j
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
        return BoxMapper.createDTO(boxes);
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
    public void addBox(Long carWashId, BoxRequest boxRequest) {
        Configuration configuration = configurationRepository.findById(carWashId).orElseThrow(
                () -> new ConfigurationNotFoundException(carWashId)
        );
        Box box = BoxMapper.createBox(configuration, boxRequest);
        boxRepository.save(box);
        log.info("Add box: {}", box);
    }

    @Override
    public void updateBox(Long boxId, BoxRequest boxRequest) {
        Box box = boxRepository.findById(boxId).orElseThrow(
                () -> new BoxNotFoundException(boxId)
        );
        box.setName(boxRequest.getName());
        boxRepository.save(box);
        log.info("Update box: {}", box);
    }

    @Override
    public void deleteBox(Long boxId) {
        boxRepository.deleteById(boxId);
        log.info("Delete box: {}", boxId);
    }

}

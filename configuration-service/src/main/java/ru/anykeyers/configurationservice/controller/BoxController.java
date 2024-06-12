package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.configuration.BoxDTO;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.service.BoxService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.BOX_NAME)
@Tag(name = "Обработка боксов автомоек")
public class BoxController {

    private final BoxService boxService;

    @Operation(summary = "Получить все боксы автомойки")
    @GetMapping("/{carWashId}")
    public List<BoxDTO> getAllBoxes(@PathVariable Long carWashId) {
        return boxService.getCarWashBoxes(carWashId);
    }

    @Operation(summary = "Получить идентификаторы боксов автомойки")
    @GetMapping("/{carWashId}/ids")
    public List<Long> getAllBoxesIds(@PathVariable Long carWashId) {
        return boxService.getCarWashBoxesIds(carWashId);
    }

    @Operation(summary = "Добавить бокс автомойке")
    @PostMapping("/{carWashId}")
    public void addBox(@PathVariable Long carWashId, @RequestBody BoxRequest boxRequest) {
        boxService.addBox(carWashId, boxRequest);
    }

    @Operation(summary = "Обновить бокс у автомойки")
    @PutMapping("/{boxId}")
    public void updateBox(@PathVariable Long boxId, @RequestBody BoxRequest boxRequest) {
        boxService.updateBox(boxId, boxRequest);
    }

    @Operation(summary = "Удалить бокс у автомойки")
    @DeleteMapping("/{boxId}")
    public void deleteBox(@PathVariable Long boxId) {
        boxService.deleteBox(boxId);
    }

}

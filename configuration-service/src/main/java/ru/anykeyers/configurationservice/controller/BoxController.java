package ru.anykeyers.configurationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.BoxDTO;
import ru.anykeyers.configurationservice.domain.box.BoxRequest;
import ru.anykeyers.configurationservice.service.BoxService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/box")
public class BoxController {

    private final BoxService boxService;

    @GetMapping("/{carWashId}")
    public List<BoxDTO> getAllBoxes(@PathVariable Long carWashId) {
        return boxService.getCarWashBoxes(carWashId);
    }

    @GetMapping("/{carWashId}/ids")
    public List<Long> getAllBoxesIds(@PathVariable Long carWashId) {
        return boxService.getCarWashBoxesIds(carWashId);
    }

    @PostMapping("/{carWashId}")
    public BoxDTO addBox(@PathVariable Long carWashId, @RequestBody BoxRequest boxRequest) {
        return boxService.addBox(carWashId, boxRequest);
    }

}

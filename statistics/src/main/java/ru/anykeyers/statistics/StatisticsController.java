package ru.anykeyers.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.anykeyers.statistics.domain.StatisticsDTO;
import ru.anykeyers.statistics.service.StatisticsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{carWashId}")
    public StatisticsDTO getStatistics(@PathVariable Long carWashId) {
        return statisticsService.getStatistics(carWashId);
    }

}

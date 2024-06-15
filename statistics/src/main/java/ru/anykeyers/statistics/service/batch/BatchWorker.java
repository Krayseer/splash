package ru.anykeyers.statistics.service.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.anykeyers.statistics.service.batch.processor.BatchProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Воркер по обработке пакетных обработчиков
 */
@Component
@RequiredArgsConstructor
public class BatchWorker {

    private final ExecutorService executorService;

    private final List<BatchProcessor> batchProcessors;

    /**
     * Обработать все пакетные обработчики
     */
    @Scheduled(fixedRate = 5000)
    public void processBatches() {
        for (BatchProcessor batchProcessor : batchProcessors) {
            executorService.execute(batchProcessor.getBatchProcessTask());
        }
    }

}

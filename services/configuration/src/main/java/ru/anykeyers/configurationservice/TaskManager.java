package ru.anykeyers.configurationservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Асинхронный обработчик заданий
 */
@Component
public class TaskManager {

    private final ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();

    private final List<Runnable> failedTasks = new ArrayList<>();

    /**
     * Добавить задание на обработку
     */
    public void addTask(Runnable task) {
        try{
            threadPool.execute(task);
        } catch (Throwable ex){
            failedTasks.add(task);
        }
    }

}

package ru.anykeyers.storageservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationContext {

    @Bean
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

}

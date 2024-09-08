package ru.anykeyers.commonsapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация по работе с удаленными сервисами
 */
@Configuration
@ComponentScan(basePackages = "ru.anykeyers.commonsapi.remote")
public class RemoteConfig {
}

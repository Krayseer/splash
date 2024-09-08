package ru.anykeyers.orderservice.context;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.anykeyers.commonsapi.config.RemoteConfig;
import ru.anykeyers.commonsapi.config.WebConfig;

/**
 * Контекст приложения
 */
@Configuration
@EnableScheduling
@EnableDiscoveryClient
@Import({ WebConfig.class, RemoteConfig.class })
public class ApplicationConfig {
}

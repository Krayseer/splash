package ru.anykeyers.configurationservice.context;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.anykeyers.commonsapi.config.RemoteConfig;
import ru.anykeyers.commonsapi.config.WebConfig;

/**
 * Контекст приложения
 */
@Configuration
@EnableDiscoveryClient
@Import({ WebConfig.class, RemoteConfig.class })
public class ApplicationConfig {
}
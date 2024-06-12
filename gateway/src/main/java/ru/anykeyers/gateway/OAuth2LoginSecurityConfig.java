package ru.anykeyers.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Конфигурация авторизации OAuth2
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class OAuth2LoginSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        // Настройки сервиса обработки заказов со стороны автомойки
                        .pathMatchers("/business-order/**").authenticated()

                        // Настройки сервиса обработки конфигураций автомоек
                        .pathMatchers("/car-wash/configuration").authenticated()
                        .pathMatchers("/car-wash/box/**").authenticated()
                        .pathMatchers("/car-wash/box/**").authenticated()
                        .pathMatchers("/car-wash/invitation").authenticated()
                        .pathMatchers("/car-wash/invitation/**").authenticated()
                        .pathMatchers("/car-wash/employee").authenticated()
                        .pathMatchers("/car-wash/employee/**").authenticated()

                        // Настройки сервиса отправки уведомлений
                        .pathMatchers("/notification/push").authenticated()
                        .pathMatchers("/notification/push/**").authenticated()

                        // Настройки сервиса обработки заказов
                        .pathMatchers("/order/**").authenticated()

                        // Настройки сервиса обработки пользователей
                        .pathMatchers(HttpMethod.GET, "/auth-server/user").authenticated()
                        .pathMatchers("/auth-server/roles").authenticated()
                        .pathMatchers("/auth-server/photo").authenticated()
                        .pathMatchers("/auth-server/setting").authenticated()

                        .anyExchange().permitAll()
                )
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

}

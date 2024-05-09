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
                        // Сервер авторизации
                        .pathMatchers(HttpMethod.POST, "/user").permitAll()

                        // Сервис конфигурирования автомоек
                        .pathMatchers("/configuration/**").permitAll()

                        // Сервис обработки услуг
                        .pathMatchers("/service/**").permitAll()

                        // Сервис обработки заказов
                        .pathMatchers("/order/user").authenticated()
                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

}

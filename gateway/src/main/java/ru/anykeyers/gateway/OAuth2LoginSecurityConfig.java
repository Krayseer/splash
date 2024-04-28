package ru.anykeyers.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Конфигурация авторизации OAuth2
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class OAuth2LoginSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/user/register").permitAll()
                        .pathMatchers("/configuration").permitAll()
                        .pathMatchers("/configuration/**").permitAll()
                        .pathMatchers("/configuration/auth").authenticated()
                        .pathMatchers("/service/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(withDefaults())
                .cors().disable()
                .csrf().disable()
                .build();
    }

}

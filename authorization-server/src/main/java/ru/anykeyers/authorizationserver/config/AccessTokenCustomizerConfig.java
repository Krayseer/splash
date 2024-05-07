package ru.anykeyers.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import ru.anykeyers.authorizationserver.domain.entity.Permission;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.authorizationserver.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Конфигурация пользовательского токена доступа
 */
@Configuration(proxyBeanMethods = false)
public class AccessTokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(RoleRepository roleRepository) {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims(claim -> {
                    List<Role> roles = roleRepository.findByRoleCodeIn(
                        context.getPrincipal().getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                    );
                    claim.put("authorities", roles.stream().map(Role::getRoleCode).collect(Collectors.toSet()));
                });
            }
        };
    }

}

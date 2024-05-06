package ru.anykeyers.authorizationserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import ru.anykeyers.authorizationserver.repository.JdbcClientRegistrationRepository;
import ru.anykeyers.authorizationserver.repository.OAuth2ClientRoleRepository;
import ru.anykeyers.authorizationserver.repository.UserRepository;
import ru.anykeyers.authorizationserver.service.impl.AuthorityMappingOAuth2UserService;
import ru.anykeyers.authorizationserver.service.impl.JdbcUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Стандартная кофнигурация Spring Security
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class DefaultSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   UserRepositoryOAuth2UserHandler userHandler) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .oauth2Login(oauth2login -> {
                    SavedUserAuthenticationSuccessHandler successHandler = new SavedUserAuthenticationSuccessHandler();
                    successHandler.setOauth2UserHandler(userHandler);
                    oauth2login.successHandler(successHandler);
                })
                .build();
    }


    /**
     * Класс контейнера информации о пользователе, используемый для получения информации о пользователе
     * во время аутентификации с помощью формы.
     */
    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new JdbcUserDetailsService(userRepository);
    }

    /**
     * Расширенная информация о разрешении сопоставления входа в систему OAuth2.
     */
    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> auth2UserService(OAuth2ClientRoleRepository oAuth2ClientRoleRepository) {
        return new AuthorityMappingOAuth2UserService(oAuth2ClientRoleRepository);
    }

    /**
     * Постоянный GitHub клиент
     */
    @Bean
    ClientRegistrationRepository clientRegistrationRepository(JdbcTemplate jdbcTemplate,
                                                              @Value("${application.auth-provider.registration-id}") String registrationId,
                                                              @Value("${application.auth-provider.client-name}") String clientName,
                                                              @Value("${application.auth-provider.client-id}") String clientId,
                                                              @Value("${application.auth-provider.client-secret}") String clientSecret,
                                                              @Value("${application.auth-provider.authorization-uri}") String authorizationUri,
                                                              @Value("${application.auth-provider.token-uri}") String tokenUri,
                                                              @Value("${application.auth-provider.redirect-uri}") String redirectUri,
                                                              @Value("${application.auth-provider.user-info-uri}") String userInfoUri) {
        JdbcClientRegistrationRepository jdbcClientRegistrationRepository = new JdbcClientRegistrationRepository(jdbcTemplate);
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId(registrationId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectUri)
                .scope("read:user")
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName("login")
                .clientName(clientName)
                .build();
        jdbcClientRegistrationRepository.save(clientRegistration);
        return jdbcClientRegistrationRepository;
    }

    /**
     * Отвечает за сохранение OAuth2AuthorizedClient между веб-запросами.
     */
    @Bean
    OAuth2AuthorizedClientService authorizedClientService(JdbcTemplate jdbcTemplate,
                                                          ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }

    /**
     * Используется для сохранения и сохранения авторизованных клиентов между запросами.
     */
    @Bean
    OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

}

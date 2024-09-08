package ru.anykeyers.authorizationserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.CollectionUtils;
import ru.anykeyers.authorizationserver.domain.entity.OAuth2ClientRole;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.authorizationserver.repository.OAuth2ClientRoleRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Служба сопоставления разрешений, пользователям входа в систему OAuth2 будут предоставлены соответствующие разрешения.
 * Если разрешение сопоставления пусто, будет предоставлено самое низкое разрешение ROLE_OPERATION.
 * Примечание. В этом примере `authority` и `role` — это настраиваемые поля информации о разрешениях, которые не указаны
 * в протоколе OAuth2 и протоколе OpenID Connect.
 */
@RequiredArgsConstructor
public class AuthorityMappingOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    private final OAuth2ClientRoleRepository oAuth2ClientRoleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) delegate.loadUser(userRequest);
        Map<String, Object> additionalParameters = userRequest.getAdditionalParameters();
        Set<String> role = new HashSet<>();
        if (additionalParameters.containsKey("authority")) {
            role.addAll((Collection<? extends String>) additionalParameters.get("authority"));
        }
        if (additionalParameters.containsKey("role")) {
            role.addAll((Collection<? extends String>) additionalParameters.get("role"));
        }
        Set<SimpleGrantedAuthority> mappedAuthorities = role.stream()
                .map(r -> oAuth2ClientRoleRepository.findByClientRegistrationIdAndRoleCode(
                        userRequest.getClientRegistration().getRegistrationId(), r
                ))
                .map(OAuth2ClientRole::getRole)
                .map(Role::getRoleCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(mappedAuthorities)) {
            mappedAuthorities = new HashSet<>(
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERATION")));
        }
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        return new DefaultOAuth2User(mappedAuthorities, oAuth2User.getAttributes(), userNameAttributeName);
    }

}

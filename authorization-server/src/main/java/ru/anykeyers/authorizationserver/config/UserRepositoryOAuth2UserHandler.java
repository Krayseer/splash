package ru.anykeyers.authorizationserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ru.anykeyers.authorizationserver.domain.entity.Role;
import ru.anykeyers.authorizationserver.domain.entity.User;
import ru.anykeyers.authorizationserver.repository.RoleRepository;
import ru.anykeyers.authorizationserver.repository.UserRepository;

import java.util.Collections;
import java.util.function.Consumer;

/**
 * DAO для сохранения пользователя OAuth2 в таблицу информации о пользователе
 */
@Component
@RequiredArgsConstructor
final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void accept(OAuth2User oAuth2User) {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) oAuth2User;
        if (userRepository.findUserByUsername(oAuth2User.getName()) != null) {
            return;
        }
        User user = new User();
        user.setUsername(defaultOAuth2User.getName());
        Role role = roleRepository.findByRoleCode(defaultOAuth2User.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_OPERATION"));
        user.setRoleList(Collections.singletonList(role));
        userRepository.save(user);
    }

}

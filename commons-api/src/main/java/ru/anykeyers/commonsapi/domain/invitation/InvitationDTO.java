package ru.anykeyers.commonsapi.domain.invitation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anykeyers.commonsapi.domain.dto.configuration.ConfigurationSimpleDTO;

import java.util.List;

/**
 * Данные о приглашении
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {

    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя пользователя, кому отправляется приглашение
     */
    private String username;

    /**
     * Автомойки
     */
    private ConfigurationSimpleDTO configuration;

    /**
     * Роли
     */
    private List<String> roles;

    /**
     * Статус заказа
     */
    private InvitationState invitationState;

}

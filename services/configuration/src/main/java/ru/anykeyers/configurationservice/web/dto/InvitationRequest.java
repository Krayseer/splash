package ru.anykeyers.configurationservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Данные о приглашении
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {

    /**
     * Имя пользователя, кому отправляется приглашение
     */
    private String username;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

    /**
     * Роли
     */
    private List<String> roles;

}

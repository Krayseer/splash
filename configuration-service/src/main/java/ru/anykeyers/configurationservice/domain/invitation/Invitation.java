package ru.anykeyers.configurationservice.domain.invitation;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.commonsapi.domain.invitation.InvitationState;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;

import java.util.List;

/**
 * Приглашение
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, кому отправлено приглашение
     */
    private String username;

    /**
     * Идентификатор автомойки
     */
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_ID")
    private Configuration configuration;

    /**
     * Список ролей
     */
    @ElementCollection
    private List<String> roles;

    /**
     * Состояние приглашения
     */
    @Enumerated(EnumType.STRING)
    private InvitationState invitationState;

}

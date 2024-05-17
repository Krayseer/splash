package ru.anykeyers.configurationservice.domain.invitation;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Приглашение
 */
@Getter
@Setter
@Entity
@Builder
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
    private Long carWashId;

    /**
     * Список ролей
     */
    @ElementCollection
    private List<String> roles;

}

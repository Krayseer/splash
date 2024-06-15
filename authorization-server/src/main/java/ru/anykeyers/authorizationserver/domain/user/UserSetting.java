package ru.anykeyers.authorizationserver.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Настройки пользователя
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Разрешена отправка push уведомлений
     */
    private boolean pushEnabled;

    /**
     * Разрешена отправка уведомлений по email
     */
    private boolean emailEnabled;

    /**
     * Пользователь, которому принадлежат настройки
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

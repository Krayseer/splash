package ru.anykeyers.authorizationserver.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean pushEnabled;

    private boolean emailEnabled;

    /**
     * Пользователь, которому принадлежат настройки
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

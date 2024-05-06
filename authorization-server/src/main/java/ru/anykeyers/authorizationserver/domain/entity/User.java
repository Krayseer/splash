package ru.anykeyers.authorizationserver.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Пользователь
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
public class User {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * Имя
     */
    @Column(name = "NAME")
    private String name;

    /**
     * Фамилия
     */
    @Column(name = "SURNAME")
    private String surname;

    /**
     * Пароль
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Почта
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * Номер телефона
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * URL фотографии
     */
    @Column(name = "PHOTO_URL")
    private String photoUrl;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_mtm_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private List<Role> roleList;

    /**
     * Время создания аккаунта
     */
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

}

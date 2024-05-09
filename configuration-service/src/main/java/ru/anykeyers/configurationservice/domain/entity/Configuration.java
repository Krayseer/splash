package ru.anykeyers.configurationservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.configurationservice.domain.TypeOrganization;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONFIGURATION")
public class Configuration {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя хозяина автомойки
     */
    private String username;

    /**
     * ИНН
     */
    private String tin;

    /**
     * Тип организации
     */
    @Enumerated(EnumType.STRING)
    private TypeOrganization typeOrganization;

    /**
     * Почта
     */
    private String email;

    /**
     * Название автомойки
     */
    private String name;

    /**
     * Описание автомойки
     */
    private String description;

    /**
     * Телефонный номер
     */
    private String phoneNumber;

    /**
     * Адрес
     */
    private String address;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private List<Box> boxes;

    /**
     * Время регистрации автомойки
     */
    private Instant createdAt;

}

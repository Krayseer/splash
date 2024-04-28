package ru.anykeyers.configurationservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private String typeOrg;

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

    /**
     * Время регистрации автомойки
     */
    private LocalDateTime createdAt;

}

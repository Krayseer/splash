package ru.anykeyers.configurationservice.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.configurationservice.domain.configuration.Configuration;

/**
 * Работник
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Автомойка, которой принадлежнит работник
     */
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_ID")
    private Configuration configuration;

}

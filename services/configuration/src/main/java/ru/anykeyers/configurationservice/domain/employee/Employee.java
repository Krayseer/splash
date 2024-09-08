package ru.anykeyers.configurationservice.domain.employee;

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
@ToString
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
     * Имя пользователя
     */
    private String username;

    /**
     * Автомойка, которой принадлежнит работник
     */
    @ManyToOne
    @JoinColumn(name = "CONFIGURATION_ID")
    private Configuration configuration;

}

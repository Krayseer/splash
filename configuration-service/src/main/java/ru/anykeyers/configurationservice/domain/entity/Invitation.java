package ru.anykeyers.configurationservice.domain.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Long carWashId;

    @ElementCollection
    private List<String> roles;

}

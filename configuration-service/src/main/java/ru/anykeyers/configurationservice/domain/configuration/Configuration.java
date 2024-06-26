package ru.anykeyers.configurationservice.domain.configuration;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.configurationservice.domain.TypeOrganization;
import ru.anykeyers.configurationservice.domain.box.Box;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Конфигурация автомойки
 */
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

    /**
     * Долгота
     */
    private String longitude;

    /**
     * Широта
     */
    private String latitude;

    /**
     * Время открытия
     */
    private String openTime;

    /**
     * Время закрытия
     */
    private String closeTime;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private List<Box> boxes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "configuration_photo_urls",
            joinColumns = @JoinColumn(name = "configuration_id")
    )
    @Column(name = "photo_url")
    private List<String> photoUrls;

    /**
     * Время регистрации автомойки
     */
    private Instant createdAt;

    /**
     * Включен ли режим обработки заказов менеджерами
     */
    private boolean managementProcessOrders;

    /**
     * Включен режим самообслуживания
     */
    private boolean selfService;

    public void addPhotoUrls(List<String> photoUrls) {
        if (this.photoUrls == null) {
            this.photoUrls = new ArrayList<>();
        }
        this.photoUrls.addAll(photoUrls);
    }

}

package ru.anykeyers.configurationservice.domain;

import jakarta.persistence.*;
import lombok.*;
import ru.anykeyers.commonsapi.domain.Address;
import ru.anykeyers.commonsapi.domain.Interval;
import ru.anykeyers.commonsapi.domain.configuration.OrderProcessMode;
import ru.anykeyers.commonsapi.domain.configuration.OrganizationInfo;

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
     * Информация об организации
     */
    private OrganizationInfo organizationInfo;
    /**
     * Адрес организации
     */
    private Address address;
    /**
     * Время работы
     */
    private Interval workTime;
    /**
     * Список боксов
     */
    @OneToMany(
            mappedBy = "configuration",
            cascade = CascadeType.ALL
    )
    private List<Box> boxes;
    /**
     * Список фотографий
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "configuration_photo_urls",
            joinColumns = @JoinColumn(
                    name = "configuration_id"
            )
    )
    @Column(
            name = "photo_url"
    )
    private List<String> photoUrls;
    /**
     * Время регистрации автомойки
     */
    private Instant createdAt;
    /**
     * Режим обработки заказов
     */
    private OrderProcessMode orderProcessMode;

    public void addPhotoUrls(List<String> photoUrls) {
        if (this.photoUrls == null) {
            this.photoUrls = new ArrayList<>();
        }
        this.photoUrls.addAll(photoUrls);
    }

}

package ru.anykeyers.commonsapi.domain.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные об адресе для сообщения
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddress {

    /**
     * Адрес
     */
    private String address;

}

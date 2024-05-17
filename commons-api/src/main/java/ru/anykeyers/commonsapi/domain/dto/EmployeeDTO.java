package ru.anykeyers.commonsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о работнике
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Идентификатор автомойки
     */
    private Long carWashId;

}

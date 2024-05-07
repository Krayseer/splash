package ru.anykeyers.commonsapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxDTO {

    private Long id;

    private String name;

    private Long carWashId;

}

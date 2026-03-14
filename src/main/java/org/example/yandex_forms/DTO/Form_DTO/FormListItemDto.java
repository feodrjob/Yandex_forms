package org.example.yandex_forms.DTO.Form_DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class FormListItemDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdAt;
    private int questionCount;


}

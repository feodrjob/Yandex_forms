package org.example.yandex_forms.DTO.Form_DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OptionDto {
    @NotBlank(message = "Текст опции не может быть пустым")
    private String value;
    @NotNull(message = "Порядковый номер опции обязателен")
    private int orderIndex;

}

package org.example.yandex_forms.DTO.Form_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Вариант ответа для вопроса с типом RADIO или CHECKBOX")
public class OptionDto {

    @Schema(description = "Текст варианта ответа", example = "Да", required = true)
    @NotBlank(message = "Текст опции не может быть пустым")
    private String value;

    @Schema(description = "Порядковый номер варианта (для сортировки)", example = "1", required = true)
    @Min(value = 0, message = "Порядковый номер должен быть неотрицательным числом")
    private int orderIndex;
}
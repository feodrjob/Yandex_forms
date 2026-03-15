package org.example.yandex_forms.DTO.Form_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.yandex_forms.Entityes.QuestionType;

import java.util.List;

@Data
@Schema(description = "DTO вопроса, входящего в состав формы")
public class QuestionDto {

    @Schema(description = "Тип вопроса (TEXT, RADIO, CHECKBOX)", example = "RADIO", required = true)
    @NotNull(message = "Тип вопроса не может быть null")
    private QuestionType type;

    @Schema(description = "Текст вопроса", example = "Вам нравится Java?", required = true)
    @NotBlank(message = "Текст вопроса не может быть пустым")
    private String label;

    @Schema(description = "Обязательность вопроса", example = "true", required = true)
    @NotNull(message = "Обязательность вопроса должна быть указана")
    private Boolean required;

    @Schema(description = "Порядковый номер вопроса в форме", example = "1", required = true)
    @Min(value = 0, message = "Порядковый номер должен быть неотрицательным")
    private int orderIndex;

    @Schema(description = "Список вариантов ответа (только для RADIO/CHECKBOX)")
    @Valid
    private List<OptionDto> options;
}

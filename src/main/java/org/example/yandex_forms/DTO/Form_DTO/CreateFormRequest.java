package org.example.yandex_forms.DTO.Form_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на создание новой формы")
public class CreateFormRequest {

    @Schema(description = "Название формы", example = "Опрос о языке программирования", required = true)
    @NotBlank(message = "Название формы не может быть пустым")
    private String title;

    @Schema(description = "Описание формы", example = "Опрос для выявления предпочтений в языках программирования")
    private String description;

    @Schema(description = "Список вопросов формы", required = true)
    @NotNull(message = "Список вопросов не может быть null")
    @Size(min = 1, message = "Форма должна содержать хотя бы один вопрос")
    @Valid
    private List<QuestionDto> questions;
}
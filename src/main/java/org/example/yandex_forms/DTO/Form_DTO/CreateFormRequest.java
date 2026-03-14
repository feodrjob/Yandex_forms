package org.example.yandex_forms.DTO.Form_DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateFormRequest {
    @NotBlank
    private String title;
    private String description;
    @NotNull(message = "Список вопросов не может быть null")
    @Size(min = 1, message = "Форма должна содержать хотя бы один вопрос")
    @Valid
    private List<QuestionDto> questionDtoList;

}

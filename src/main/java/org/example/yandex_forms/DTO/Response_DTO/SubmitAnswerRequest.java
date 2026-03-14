package org.example.yandex_forms.DTO.Response_DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitAnswerRequest {
    @NotNull(message = "ID вопроса обязателен")
    private Long questionId;
    @NotNull(message = "Значение ответа не может быть null")
    private String value;
}

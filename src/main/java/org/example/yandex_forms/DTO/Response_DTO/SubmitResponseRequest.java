package org.example.yandex_forms.DTO.Response_DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class SubmitResponseRequest {
    @NotNull(message = "Список ответов не может быть null")
    @Size(min = 1, message = "Должен быть хотя бы один ответ")
    @Valid // <--- проверяем каждый объект SubmitAnswerRequest
    private List<SubmitAnswerRequest> answers;
}

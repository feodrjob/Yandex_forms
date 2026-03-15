package org.example.yandex_forms.DTO.Response_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на отправку ответов на форму (содержит список ответов на вопросы)")
public class SubmitResponseRequest {

    @Schema(description = "Список ответов на вопросы формы", required = true)
    @NotNull(message = "Список ответов не может быть null")
    @Size(min = 1, message = "Должен быть хотя бы один ответ")
    @Valid
    private List<SubmitAnswerRequest> answers;
}
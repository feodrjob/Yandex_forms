package org.example.yandex_forms.DTO.Response_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на отправку ответа на один вопрос")
public class SubmitAnswerRequest {

    @Schema(description = "Идентификатор вопроса, на который даётся ответ", example = "10", required = true)
    @NotNull(message = "ID вопроса обязателен")
    private Long questionId;

    @Schema(description = "Значение ответа. Для TEXT - текст, для RADIO - id опции, для CHECKBOX - id опций через запятую",
            example = "5", required = true)
    @NotBlank(message = "Значение ответа не может быть пустым") // лучше чем @NotNull для строк
    private String value;
}
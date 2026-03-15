package org.example.yandex_forms.DTO.Response_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ на конкретный вопрос в составе отправленной формы")
public class AnswerResponseDto {

    @Schema(description = "Идентификатор вопроса", example = "10")
    private Long questionId;

    @Schema(description = "Текст вопроса", example = "Ваш любимый язык программирования?")
    private String questionLabel;

    @Schema(description = "Значение ответа (текст или идентификаторы опций через запятую)",
            example = "Java")
    private String value;
}
package org.example.yandex_forms.DTO.Response_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Полная информация об ответе на форму")
public class ResponseDto {

    @Schema(description = "Идентификатор ответа", example = "42")
    private Long id;

    @Schema(description = "Идентификатор формы, на которую дан ответ", example = "5")
    private Long formId;

    @Schema(description = "Название формы", example = "Опрос о языке программирования")
    private String formTitle;

    @Schema(description = "Дата и время отправки ответа", example = "2024-03-20T10:15:30")
    private LocalDateTime submittedAt;

    @Schema(description = "Список ответов на вопросы")
    private List<AnswerResponseDto> answers;
}
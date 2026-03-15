package org.example.yandex_forms.DTO.Form_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Schema(description = "Краткая информация о форме для списка")
public class FormListItemDto {

    @Schema(description = "Идентификатор формы", example = "1")
    private Long id;

    @Schema(description = "Название формы", example = "Опрос о языке программирования")
    private String title;

    @Schema(description = "Описание формы", example = "Опрос для выявления предпочтений в языках программирования")
    private String description;

    @Schema(description = "Дата создания формы", example = "2024-03-20T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Количество вопросов в форме", example = "5")
    private int questionCount;
}
package org.example.yandex_forms.DTO.Form_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.yandex_forms.DTO.User_DTO.UserResponse;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Полная информация о форме")
public class FormResponseDto {

    @Schema(description = "Идентификатор формы", example = "1")
    private Long id;

    @Schema(description = "Название формы", example = "Опрос о языке программирования")
    private String title;

    @Schema(description = "Описание формы", example = "Опрос для выявления предпочтений в языках программирования")
    private String description;

    @Schema(description = "Дата и время создания формы", example = "2024-03-20T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Дата и время последнего обновления формы", example = "2024-03-21T14:20:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Информация о создателе формы")
    private UserResponse createdBy;

    @Schema(description = "Список вопросов формы")
    private List<QuestionDto> questions;
}

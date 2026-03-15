package org.example.yandex_forms.DTO.User_DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Краткая информация о пользователе")
public class UserResponse {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Логин пользователя", example = "john_doe")
    private String username;
}
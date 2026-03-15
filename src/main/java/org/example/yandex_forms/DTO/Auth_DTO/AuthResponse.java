package org.example.yandex_forms.DTO.Auth_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с JWT токеном после успешной аутентификации")
public class AuthResponse {

    @Schema(description = "JWT токен для доступа к защищённым эндпоинтам",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Тип токена", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Логин пользователя", example = "john_doe")
    private String username;

    public AuthResponse(String token, Long id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.type = "Bearer";
    }
}
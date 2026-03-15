package org.example.yandex_forms.DTO.Auth_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию нового пользователя")
public class RegisterRequest {

    @Schema(description = "Логин пользователя", example = "john_doe", required = true)
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 20, message = "Логин должен быть от 3 до 20 символов")
    private String username;

    @Schema(description = "Пароль пользователя", example = "securePassword123", required = true)
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 20, message = "Пароль должен быть от 6 до 20 символов")
    private String password;
}
package org.example.yandex_forms.DTO.User_DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание пользователя")
public class UserCreateRequest {
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 50, message = "Логин от 3 до 50 символов")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль минимум 6 символов")
    private String password;
}

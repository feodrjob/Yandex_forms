package org.example.yandex_forms.DTO.Auth_DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;

    //Конструктор с type по умолчанию
    public AuthResponse(String token, Long id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.type = "Bearer";
    }
}

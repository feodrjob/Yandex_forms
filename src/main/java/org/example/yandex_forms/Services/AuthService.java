package org.example.yandex_forms.Services;

import org.springframework.transaction.annotation.Transactional;  // ✅ ВОТ ЭТО!
import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Auth_DTO.AuthResponse;
import org.example.yandex_forms.DTO.Auth_DTO.LoginRequest;
import org.example.yandex_forms.DTO.Auth_DTO.RegisterRequest;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.example.yandex_forms.Security.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 1. Проверяем, существует ли пользователь
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Создаём нового пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Если есть email в сущности, но нет в RegisterRequest - можно добавить или сделать опциональным
        // user.setEmail(request.getEmail());

        userRepository.save(user);

        // 3. Генерируем JWT токен
        String token = jwtUtils.generateToken(user.getUsername());

        // 4. Возвращаем ответ
        return new AuthResponse(token, user.getId(), user.getUsername());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // 1. Ищем пользователя
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Проверяем пароль
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Генерируем токен
        String token = jwtUtils.generateToken(user.getUsername());

        // 4. Возвращаем ответ
        return new AuthResponse(token, user.getId(), user.getUsername());
    }
}

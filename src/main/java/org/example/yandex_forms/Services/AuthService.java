package org.example.yandex_forms.Services;

import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Auth_DTO.AuthResponse;
import org.example.yandex_forms.DTO.Auth_DTO.LoginRequest;
import org.example.yandex_forms.DTO.Auth_DTO.RegisterRequest;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Exception.InvalidCredentialsException;
import org.example.yandex_forms.Exception.UsernameAlreadyExistsException;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.example.yandex_forms.Security.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getUsername());

        return new AuthResponse(token, user.getId(), user.getUsername());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return new AuthResponse(token, user.getId(), user.getUsername());
    }
}
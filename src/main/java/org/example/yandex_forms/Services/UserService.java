package org.example.yandex_forms.Services;



import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Автоматически создаем конструктор для всех final
@Transactional (readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register (String username, String rawPassword) {
        if (userRepository.existsByUsername(username))
        {
            throw new RuntimeException("Username is already in use");
        }

        User user = new User();
        user.setUsername(username);

        //Хешируем пароль
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}

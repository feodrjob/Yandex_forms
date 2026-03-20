package org.example.yandex_forms.Security;

import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)// ищем пользователя в базе
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this username" + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();

    }
}

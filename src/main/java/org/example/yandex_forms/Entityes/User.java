package org.example.yandex_forms.Entityes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uc_username", columnNames = {"username"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Пользователь системы")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Column(nullable = false, length = 50)
    @Schema(description = "Имя пользователя (логин)", example = "john_doe")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Пароль (хранится в зашифрованном виде)", example = "$2a$10$...")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Дата регистрации", example = "2026-03-20T10:00:00")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @Schema(description = "Формы, созданные пользователем")
    private List<Form> forms = new ArrayList<>();

    public void addForm(Form form) {
        forms.add(form);
        form.setUser(this);
    }

    public void removeForm(Form form) {
        forms.remove(form);
        form.setUser(null);
    }
}

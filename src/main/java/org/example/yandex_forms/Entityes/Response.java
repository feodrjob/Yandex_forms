package org.example.yandex_forms.Entityes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Заполненная форма (ответ пользователя на форму)")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор ответа", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    @Schema(description = "Форма, на которую отправлен ответ")
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Пользователь, отправивший ответ")
    private User user;

    @CreationTimestamp
    @Column(name = "submitted_at", nullable = false, updatable = false)
    @Schema(description = "Дата и время отправки ответа", example = "2026-03-20T10:30:00")
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @Schema(description = "Список ответов на вопросы")
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setResponse(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setResponse(null);
    }
}
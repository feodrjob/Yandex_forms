package org.example.yandex_forms.Entityes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Вопрос внутри формы")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор вопроса", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    @Schema(description = "Форма, к которой относится вопрос")
    private Form form;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Тип вопроса: TEXT, RADIO, CHECKBOX", example = "TEXT")
    private QuestionType type;

    @Column(nullable = false)
    @Schema(description = "Текст вопроса", example = "Как вас зовут?")
    private String label;

    @Column(nullable = false)
    @Schema(description = "Обязательность ответа", example = "true")
    private boolean required;

    @Column(name = "order_index", nullable = false)
    @Schema(description = "Порядок отображения вопроса в форме", example = "0")
    private Integer orderIndex;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Schema(description = "Варианты ответов (для RADIO/CHECKBOX)")
    private List<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
        option.setQuestion(this);
    }

    public void removeOption(Option option) {
        options.remove(option);
        option.setQuestion(null);
    }
}
package org.example.yandex_forms.Entityes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на вопрос в рамках заполненной формы")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор ответа", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    @Schema(description = "Заполненная форма (Response), к которой относится ответ")
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @Schema(description = "Вопрос, на который дан ответ")
    private Question question;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Значение ответа: текст или ID вариантов (через запятую)", example = "42 или 1,3,5")
    private String answerValue;
}
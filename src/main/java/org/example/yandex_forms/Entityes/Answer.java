package org.example.yandex_forms.Entityes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {  // переименовал в единственное число

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)  // исправил имя колонки
    private Question question;  // переименовал поле в question (единственное число)

    @Column(nullable = false, length = 1000)  // добавил поле для хранения ответа
    private String answerValue;  // здесь будет текст ответа или ID опций через запятую
}
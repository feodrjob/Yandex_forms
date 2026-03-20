package org.example.yandex_forms.Entityes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Вариант ответа для вопроса типа radio/checkbox")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор варианта", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @Schema(description = "Вопрос, к которому относится вариант")
    private Question question;

    @Column(nullable = false)
    @Schema(description = "Текст варианта ответа", example = "Да")
    private String optionValue;

    @Column(name = "order_index", nullable = false)
    @Schema(description = "Порядок отображения варианта", example = "0")
    private Integer orderIndex;
}
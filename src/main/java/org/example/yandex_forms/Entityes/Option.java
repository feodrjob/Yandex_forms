package org.example.yandex_forms.Entityes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {  // название в единственном числе

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // добавил LAZY для производительности
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)  // добавил, так как value должно быть обязательным
    private String value;

    @Column(name = "order_index", nullable = false)  // добавил name и nullable
    private Integer orderIndex;
}

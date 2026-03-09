package org.example.yandex_forms.Entityes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import jakarta.persistence.Id;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "options")

public class Options {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    private String value;
    private Integer orderIndex;
}


package org.example.yandex_forms.Repositoryes;
import org.example.yandex_forms.Entityes.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByResponseId(Long responseId);
    List<Answer> findAllByQuestionId(Long questionId);
}
package org.example.yandex_forms.Repositoryes;

import jakarta.transaction.Transactional;
import org.example.yandex_forms.Entityes.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByQuestionId(Long questionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Option o WHERE o.question.id = :questionId") // убрал пробел после =:
    void deleteAllByQuestionId(Long questionId);
}
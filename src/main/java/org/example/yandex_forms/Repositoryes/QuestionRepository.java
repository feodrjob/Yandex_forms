package org.example.yandex_forms.Repositoryes;
import jakarta.transaction.Transactional;
import org.example.yandex_forms.Entityes.Question;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByFormId(Long formId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Question q WHERE q.form.id = :formId")
    void deleteAllByFormId(Long formId);
}
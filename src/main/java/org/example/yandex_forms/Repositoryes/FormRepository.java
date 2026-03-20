package org.example.yandex_forms.Repositoryes;
import org.example.yandex_forms.Entityes.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByUserId(Long userId);

    // Пагинированный список без фильтрации
    Page<Form> findAllByUserId(Long userId, Pageable pageable);

    // Пагинированный список с фильтром по названию (без учёта регистра)
    Page<Form> findAllByUserIdAndTitleContainingIgnoreCase(Long userId, String title, Pageable pageable);
}
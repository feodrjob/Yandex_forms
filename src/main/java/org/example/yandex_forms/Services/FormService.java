package org.example.yandex_forms.Services;

import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Form_DTO.CreateFormRequest;
import org.example.yandex_forms.DTO.Form_DTO.OptionDto;
import org.example.yandex_forms.DTO.Form_DTO.QuestionDto;
import org.example.yandex_forms.Entityes.Form;
import org.example.yandex_forms.Entityes.Option;
import org.example.yandex_forms.Entityes.Question;
import org.example.yandex_forms.Entityes.QuestionType;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.FormRepository;
import org.example.yandex_forms.Repositoryes.QuestionRepository;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.example.yandex_forms.Exception.FormNotFoundException;
import org.example.yandex_forms.Exception.InvalidFormDataException;
import org.example.yandex_forms.Exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FormService {

    private final UserRepository userRepository;
    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;

    private void validateQuestion(QuestionDto qDto) {
        if (qDto.getType() == QuestionType.TEXT) {
            if (qDto.getOptions() != null && !qDto.getOptions().isEmpty()) {
                throw new InvalidFormDataException("Вопрос типа TEXT не может содержать варианты ответов");
            }
        } else if (qDto.getType() == QuestionType.RADIO || qDto.getType() == QuestionType.CHECKBOX) {
            if (qDto.getOptions() == null || qDto.getOptions().isEmpty()) {
                throw new InvalidFormDataException("Вопрос типа " + qDto.getType() + " должен содержать хотя бы один вариант ответа");
            }
        }
    }

    @Transactional
    public Form createForm(Long userId, CreateFormRequest request) {
        // Проверка пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Валидация всех вопросов
        request.getQuestions().forEach(this::validateQuestion);

        Form form = new Form();
        form.setUser(user);
        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());

        for (QuestionDto qDto : request.getQuestions()) {
            Question question = new Question();
            question.setType(qDto.getType());
            question.setLabel(qDto.getLabel());
            question.setRequired(qDto.getRequired());
            question.setOrderIndex(qDto.getOrderIndex());

            form.addQuestion(question);

            if (qDto.getOptions() != null && !qDto.getOptions().isEmpty()) {
                for (OptionDto oDto : qDto.getOptions()) {
                    Option option = new Option();
                    option.setOptionValue(oDto.getValue());   // правильный сеттер
                    option.setOrderIndex(oDto.getOrderIndex());
                    question.addOption(option);
                }
            }
        }

        return formRepository.save(form);
    }

    public Form getFormById(Long formId) {
        return formRepository.findById(formId)
                .orElseThrow(() -> new FormNotFoundException("Form not found with id: " + formId));
    }

    public List<Form> getUserForms(Long userId) {
        return getUserForms(userId, null, "createdAt", "desc", 0, 20);
    }

    // Расширенный метод с фильтрацией, сортировкой и пагинацией
    public List<Form> getUserForms(Long userId, String title, String sortBy, String direction, int page, int size) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        // Белый список полей для сортировки
        Set<String> allowedSortFields = new HashSet<>(Arrays.asList("title", "createdAt", "updatedAt", "id"));
        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));

        Page<Form> pageResult;
        if (title != null && !title.isBlank()) {
            pageResult = formRepository.findAllByUserIdAndTitleContainingIgnoreCase(userId, title, pageable);
        } else {
            pageResult = formRepository.findAllByUserId(userId, pageable);
        }

        return pageResult.getContent();
    }

    private Form findFormAndCheckOwnership(Long formId, Long userId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new FormNotFoundException("Form not found with id: " + formId));
        if (!form.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this form");
        }
        return form;
    }

    @Transactional
    public void deleteForm(Long formId, Long userId) {
        Form form = findFormAndCheckOwnership(formId, userId);
        formRepository.delete(form);
    }

    @Transactional
    public Form updateForm(Long formId, Long userId, CreateFormRequest request) {
        Form form = findFormAndCheckOwnership(formId, userId);

        // Валидация новых вопросов
        request.getQuestions().forEach(this::validateQuestion);

        // Удаляем старые вопросы (и опции каскадно)
        questionRepository.deleteAllByFormId(formId);

        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());

        for (QuestionDto qDto : request.getQuestions()) {
            Question question = new Question();
            question.setType(qDto.getType());
            question.setLabel(qDto.getLabel());
            question.setRequired(qDto.getRequired());
            question.setOrderIndex(qDto.getOrderIndex());

            form.addQuestion(question);

            if (qDto.getOptions() != null && !qDto.getOptions().isEmpty()) {
                for (OptionDto oDto : qDto.getOptions()) {
                    Option option = new Option();
                    option.setOptionValue(oDto.getValue());   // правильный сеттер
                    option.setOrderIndex(oDto.getOrderIndex());
                    question.addOption(option);
                }
            }
        }

        return formRepository.save(form);
    }
}
package org.example.yandex_forms.Services;

import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Form_DTO.CreateFormRequest;
import org.example.yandex_forms.DTO.Form_DTO.OptionDto;
import org.example.yandex_forms.DTO.Form_DTO.QuestionDto;
import org.example.yandex_forms.Entityes.Form;
import org.example.yandex_forms.Entityes.Option;
import org.example.yandex_forms.Entityes.Question;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.FormRepository;
import org.example.yandex_forms.Repositoryes.QuestionRepository;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Автоматически создаем конструктор для всех final
@Transactional(readOnly = true)
public class FormService
{
    private final UserRepository userRepository;
    private final FormRepository formRepository;
    private  final QuestionRepository questionRepository;

    @Transactional
    public Form createForm(Long userId, CreateFormRequest request) {
        // 1. Найти пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // 2. Создать форму
        Form form = new Form();
        form.setUser(user);
        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());
        // 3. Для каждого вопроса из request.getQuestions() создать Question, заполнить поля
        for (QuestionDto qDto : request.getQuestions())
        {
            Question question = new Question();
            question.setType(qDto.getType());
            question.setLabel(qDto.getLabel());
            question.setRequired(qDto.getRequired());
            question.setOrderIndex(qDto.getOrderIndex());

            form.addQuestion(question);

            // 4. Для каждого вопроса, если тип RADIO/CHECKBOX и есть options, создать Option и привязать

            if (qDto.getOptions() != null && !qDto.getOptions().isEmpty()) {
                for (OptionDto optionDto : qDto.getOptions()) {
                    Option option = new Option();
                    option.setValue(optionDto.getValue());
                    option.setOrderIndex(optionDto.getOrderIndex());
                    question.addOption(option);
                }
            }
        }
        return formRepository.save(form);
    }

    public Form getFormById(Long formId)
    {
        return formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));
    }

    public List<Form> getUserForms(Long userId)
    {
        if (!userRepository.existsById(userId))
        {
            throw new RuntimeException("Form not found");
        }
        return formRepository.findAllByUserId(userId);
    }

    private Form findFormAndCheckOwnership(Long formId, Long userId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Форма с id " + formId + " не найдена"));
        if (!form.getUser().getId().equals(userId)) {
            throw new RuntimeException("Вы не являетесь владельцем этой формы");
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
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Форма с id " + formId + " не найдена"));
        if (!form.getUser().getId().equals(userId)) {
            throw new RuntimeException("Вы не являетесь владельцем этой формы");
        }

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
                    option.setValue(oDto.getValue());
                    option.setOrderIndex(oDto.getOrderIndex());
                    question.addOption(option);
                }
            }
        }

        return formRepository.save(form);
    }

}

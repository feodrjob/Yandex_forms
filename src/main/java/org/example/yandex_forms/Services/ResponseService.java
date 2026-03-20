package org.example.yandex_forms.Services;

import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Response_DTO.SubmitAnswerRequest;
import org.example.yandex_forms.DTO.Response_DTO.SubmitResponseRequest;
import org.example.yandex_forms.Entityes.*;
import org.example.yandex_forms.Repositoryes.*;
import org.example.yandex_forms.Exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResponseService {

    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public void submitResponse(Long userId, Long formId, SubmitResponseRequest request) {
        // 1. Проверяем существование формы
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new FormNotFoundException("Form not found with id: " + formId));

        // 2. Загружаем все вопросы формы с опциями (оптимизация)
        List<Question> questions = questionRepository.findAllByFormId(formId);
        if (questions.isEmpty()) {
            throw new InvalidFormDataException("Form has no questions");
        }

        // Строим мапу вопросов для быстрого доступа
        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // Для каждого вопроса создаём мапу опций (если есть)
        Map<Long, Set<Long>> questionOptionIdsMap = new HashMap<>();
        for (Question q : questions) {
            if (q.getType() == QuestionType.RADIO || q.getType() == QuestionType.CHECKBOX) {
                Set<Long> optionIds = q.getOptions().stream()
                        .map(Option::getId)
                        .collect(Collectors.toSet());
                questionOptionIdsMap.put(q.getId(), optionIds);
            }
        }

        // 3. Валидация каждого ответа
        for (SubmitAnswerRequest answerReq : request.getAnswers()) {
            Question question = questionMap.get(answerReq.getQuestionId());
            if (question == null) {
                throw new InvalidAnswerException("Question not found: " + answerReq.getQuestionId());
            }

            // Проверка принадлежности вопроса форме (уже гарантировано мапой)
            if (!question.getForm().getId().equals(formId)) {
                throw new InvalidAnswerException("Question " + answerReq.getQuestionId() + " does not belong to this form");
            }

            String value = answerReq.getValue();
            if (question.isRequired() && (value == null || value.isBlank())) {
                throw new RequiredQuestionMissingException("Required question " + question.getId() + " has no answer");
            }

            // Валидация в зависимости от типа вопроса
            switch (question.getType()) {
                case TEXT:
                    // Можно добавить ограничения на длину, но не обязательно
                    break;
                case RADIO:
                    // Ожидается один id опции
                    Long optionId = parseOptionId(value);
                    Set<Long> allowedIds = questionOptionIdsMap.get(question.getId());
                    if (!allowedIds.contains(optionId)) {
                        throw new InvalidAnswerException("Invalid option id " + optionId + " for question " + question.getId());
                    }
                    break;
                case CHECKBOX:
                    // Ожидается строка с id через запятую, например "1,3,5"
                    List<Long> selectedIds = parseOptionIdsList(value);
                    Set<Long> allowed = questionOptionIdsMap.get(question.getId());
                    for (Long id : selectedIds) {
                        if (!allowed.contains(id)) {
                            throw new InvalidAnswerException("Invalid option id " + id + " for question " + question.getId());
                        }
                    }
                    break;
            }
        }

        // 4. Создаём Response
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Response response = new Response();
        response.setForm(form);
        response.setUser(user);
        // submittedAt устанавливается автоматически @CreationTimestamp

        responseRepository.save(response);

        // 5. Сохраняем Answer (уже валидированные значения)
        for (SubmitAnswerRequest answerReq : request.getAnswers()) {
            Answer answer = new Answer();
            answer.setResponse(response);
            answer.setQuestion(questionRepository.getReferenceById(answerReq.getQuestionId()));
            answer.setAnswerValue(answerReq.getValue()); // сохраняем строку как есть (текст или id/id через запятую)
            answerRepository.save(answer);
        }
    }

    private Long parseOptionId(String value) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidAnswerException("Option id must be a number: " + value);
        }
    }

    private List<Long> parseOptionIdsList(String value) {
        if (value == null || value.isBlank()) return Collections.emptyList();
        String[] parts = value.split(",");
        List<Long> ids = new ArrayList<>();
        for (String part : parts) {
            try {
                ids.add(Long.parseLong(part.trim()));
            } catch (NumberFormatException e) {
                throw new InvalidAnswerException("Invalid option id format: " + part);
            }
        }
        return ids;
    }
}
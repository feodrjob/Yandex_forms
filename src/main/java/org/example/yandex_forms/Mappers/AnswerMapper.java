package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Response_DTO.AnswerResponseDto;
import org.example.yandex_forms.DTO.Response_DTO.SubmitAnswerRequest;
import org.example.yandex_forms.Entityes.Answer;
import org.example.yandex_forms.Entityes.Question;
import org.example.yandex_forms.Entityes.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface AnswerMapper {

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "questionLabel", source = "question.label")
    @Mapping(target = "value", source = "answerValue")
    AnswerResponseDto toResponse(Answer answer);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "response", source = "response")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "answerValue", source = "request.value")
    Answer toEntity(SubmitAnswerRequest request,
                    Response response,
                    Question question);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "response", ignore = true)  // установим позже
    @Mapping(target = "question", ignore = true)  // установим в сервисе
    @Mapping(target = "answerValue", source = "value")
    Answer fromSubmitRequest(SubmitAnswerRequest request);


    @Named("createAnswerWithQuestion")
    default Answer createAnswerWithQuestion(SubmitAnswerRequest request, Question question) {
        if (request == null || question == null) return null;

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerValue(request.getValue());
        return answer;
    }
}

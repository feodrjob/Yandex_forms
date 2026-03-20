package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Response_DTO.ResponseDto;
import org.example.yandex_forms.DTO.Response_DTO.SubmitResponseRequest;
import org.example.yandex_forms.Entityes.Answer;
import org.example.yandex_forms.Entityes.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = AnswerMapper.class)
public interface ResponseMapper {

    @Mapping(target = "formId", source = "form.id")
    @Mapping(target = "formTitle", source = "form.title")
    @Mapping(target = "submittedAt", source = "submittedAt")
    @Mapping(target = "answers", source = "answers")
    ResponseDto toDto(Response response);

    List<ResponseDto> toDtoList(List<Response> responses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "form", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Response toEntity(SubmitResponseRequest request);

    @Named("createResponseWithAnswers")
    default Response createResponseWithAnswers(
            org.example.yandex_forms.Entityes.Form form,
            org.example.yandex_forms.Entityes.User user,
            List<Answer> answers) {

        if (form == null || user == null) return null;

        Response response = new Response();
        response.setForm(form);
        response.setUser(user);

        if (answers != null) {
            answers.forEach(answer -> {
                answer.setResponse(response);
                response.addAnswer(answer);
            });
        }

        return response;
    }
}
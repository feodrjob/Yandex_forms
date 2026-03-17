package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Response_DTO.ResponseDto;
import org.example.yandex_forms.DTO.Response_DTO.SubmitResponseRequest;
import org.example.yandex_forms.Entityes.Answer;
import org.example.yandex_forms.Entityes.Response;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

public interface ResponseMapper {
    @Mapping(target = "formId", source = "form.id")
    @Mapping(target = "formTitle", source = "form.title")
    @Mapping(target = "submittedAt", source = "submittedAt")
    @Mapping(target = "answers", source = "answers")
    ResponseDto toDto(Response response);
    List<ResponseDto> toDtoList(List<Response> responses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "form", ignore = true)        // установим в сервисе
    @Mapping(target = "user", ignore = true)        // установим в сервисе
    @Mapping(target = "submittedAt", ignore = true) // @CreationTimestamp сам проставит
    @Mapping(target = "answers", ignore = true)     // установим в сервисе через кастомный метод
    Response toEntity(SubmitResponseRequest request);

    // Кастомный метод для создания Response со всеми связями
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

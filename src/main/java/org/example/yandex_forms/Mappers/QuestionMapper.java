package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Form_DTO.QuestionDto;
import org.example.yandex_forms.Entityes.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "options", source = "options")
    QuestionDto toResponse(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "form", ignore = true)
    @Mapping(target = "options", source = "options")
    Question toEntity(QuestionDto dto);

    List<Question> toEntityList(List<QuestionDto> dtos);
}

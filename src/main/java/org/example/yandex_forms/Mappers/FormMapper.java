package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Form_DTO.CreateFormRequest;
import org.example.yandex_forms.DTO.Form_DTO.FormResponseDto;
import org.example.yandex_forms.Entityes.Form;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormMapper {
    @Mapping(target = "createdBy", source = "user")
    @Mapping(target = "questions", source = "questions")
    FormResponseDto toResponse(Form form);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "responses", ignore = true)
    @Mapping(target = "questions", source = "questions")
    Form toEntity(CreateFormRequest request);

}

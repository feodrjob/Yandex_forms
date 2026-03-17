package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Form_DTO.OptionDto;
import org.example.yandex_forms.Entityes.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    OptionDto toResponse(Option option);

    List<OptionDto> toDtoList(List<Option> options);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    Option toEntity(OptionDto dto);

}

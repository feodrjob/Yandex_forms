package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.Form_DTO.OptionDto;
import org.example.yandex_forms.Entityes.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OptionMapper {

    @Mapping(target = "value", source = "optionValue")   // из сущности в DTO
    OptionDto toResponse(Option option);

    @Mapping(target = "optionValue", source = "value")   // из DTO в сущность
    Option toEntity(OptionDto dto);

    List<OptionDto> toDtoList(List<Option> options);
}
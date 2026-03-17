package org.example.yandex_forms.Mappers;

import org.example.yandex_forms.DTO.User_DTO.UserCreateRequest;
import org.example.yandex_forms.DTO.User_DTO.UserResponse;
import org.example.yandex_forms.Entityes.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "forms", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateRequest userCreate);


}

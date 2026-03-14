package org.example.yandex_forms.DTO.Form_DTO;

import lombok.Data;
import org.example.yandex_forms.DTO.User_DTO.UserResponse;

import java.time.LocalDate;
import java.util.List;
@Data
public class FormResponseDto {
private Long id;
private String title;
private String description;
private LocalDate createdAt;
private LocalDate updatedAt;
private UserResponse createdBy;
private List<QuestionDto> questions;

}

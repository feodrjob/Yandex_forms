package org.example.yandex_forms.DTO.Response_DTO;

import lombok.Data;

@Data
public class AnswerResponseDto {
    private Long questionId;
    private String questionLabel;
    private String value;
}

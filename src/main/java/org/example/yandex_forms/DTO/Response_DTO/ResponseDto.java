package org.example.yandex_forms.DTO.Response_DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class ResponseDto {

    private Long id;
    private Long formId;
    private String formTitle;
    private LocalDate submittedAt;
    private  List<AnswerResponseDto> answers;
}

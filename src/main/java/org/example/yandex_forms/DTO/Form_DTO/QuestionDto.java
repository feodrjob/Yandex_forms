package org.example.yandex_forms.DTO.Form_DTO;

import lombok.Data;
import org.example.yandex_forms.Entityes.QuestionType;

import java.util.List;
@Data
public class QuestionDto {
    private QuestionType type;
    private String label;
    private Boolean required;
    private int orderIndex;
    private List<OptionDto> options;

}

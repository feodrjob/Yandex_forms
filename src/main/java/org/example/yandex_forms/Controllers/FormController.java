package org.example.yandex_forms.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Form_DTO.CreateFormRequest;
import org.example.yandex_forms.DTO.Form_DTO.FormResponseDto;
import org.example.yandex_forms.Entityes.Form;
import org.example.yandex_forms.Mappers.FormMapper;
import org.example.yandex_forms.Services.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;
    private final FormMapper formMapper;

    @PostMapping
    public ResponseEntity<FormResponseDto> createForm(
            @RequestBody @Valid CreateFormRequest request,
            @RequestParam Long userId
    ) {
        Form form = formService.createForm(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(formMapper.toResponse(form));
    }
    // 2. ПОЛУЧИТЬ ФОРМУ ПО ID
    @GetMapping("/{formId}")
    public ResponseEntity<FormResponseDto> getForm(
            @PathVariable Long formId
    ) {
        Form form = formService.getFormById(formId);
        return ResponseEntity.ok(formMapper.toResponse(form));
    }

    // 3. ПОЛУЧИТЬ ВСЕ ФОРМЫ ПОЛЬЗОВАТЕЛЯ
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FormResponseDto>> getUserForms(
            @PathVariable Long userId
    ) {
        List<Form> forms = formService.getUserForms(userId);
        List<FormResponseDto> response = forms.stream()
                .map(formMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 4. ОБНОВИТЬ ФОРМУ (ПОЛНОСТЬЮ)
    @PutMapping("/{formId}")
    public ResponseEntity<FormResponseDto> updateForm(
            @PathVariable Long formId,
            @RequestBody @Valid CreateFormRequest request,
            @RequestParam Long userId
    ) {
        Form form = formService.updateForm(formId, userId, request);
        return ResponseEntity.ok(formMapper.toResponse(form));
    }

    // 5. УДАЛИТЬ ФОРМУ
    @DeleteMapping("/{formId}")
    public ResponseEntity<Void> deleteForm(
            @PathVariable Long formId,
            @RequestParam Long userId
    ) {
        formService.deleteForm(formId, userId);
        return ResponseEntity.noContent().build();
    }
}


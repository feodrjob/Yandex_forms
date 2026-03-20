package org.example.yandex_forms.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Form_DTO.CreateFormRequest;
import org.example.yandex_forms.DTO.Form_DTO.FormResponseDto;
import org.example.yandex_forms.Entityes.Form;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Mappers.FormMapper;
import org.example.yandex_forms.Services.FormService;
import org.example.yandex_forms.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/form")
@RequiredArgsConstructor
@Tag(name = "Form", description = "Управление формами")
public class FormController {

    private final FormService formService;
    private final FormMapper formMapper;
    private final UserService userService;
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    @Operation(summary = "Создание новой формы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Форма успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @PostMapping
    public ResponseEntity<FormResponseDto> createForm(@Valid @RequestBody CreateFormRequest request) {
        Long userId = getCurrentUserId();
        Form form = formService.createForm(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(formMapper.toResponse(form));
    }

    @Operation(summary = "Получение формы по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "404", description = "Форма не найдена")
    })
    @GetMapping("/{formId}")
    public ResponseEntity<FormResponseDto> getForm(@PathVariable Long formId) {
        Form form = formService.getFormById(formId);
        return ResponseEntity.ok(formMapper.toResponse(form));
    }

    @Operation(summary = "Получение всех своих форм с фильтрацией и пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры")
    })
    @GetMapping("/my")
    public ResponseEntity<List<FormResponseDto>> getUserForms(
            @Parameter(description = "Фильтр по названию формы (частичное совпадение)")
            @RequestParam(required = false) String title,
            @Parameter(description = "Поле для сортировки (title, createdAt, updatedAt, id)")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Направление сортировки (asc/desc)")
            @RequestParam(defaultValue = "desc") String direction,
            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы")
            @RequestParam(defaultValue = "20") int size) {
        Long userId = getCurrentUserId();
        List<Form> forms = formService.getUserForms(userId, title, sortBy, direction, page, size);
        List<FormResponseDto> response = forms.stream()
                .map(formMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Обновление формы (полная замена)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Форма не найдена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на редактирование")
    })
    @PutMapping("/{formId}")
    public ResponseEntity<FormResponseDto> updateForm(
            @PathVariable Long formId,
            @Valid @RequestBody CreateFormRequest request) {
        Long userId = getCurrentUserId();
        Form form = formService.updateForm(formId, userId, request);
        return ResponseEntity.ok(formMapper.toResponse(form));
    }

    @Operation(summary = "Удаление формы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Форма не найдена"),
            @ApiResponse(responseCode = "403", description = "Нет прав на удаление")
    })
    @DeleteMapping("/{formId}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long formId) {
        Long userId = getCurrentUserId();
        formService.deleteForm(formId, userId);
        return ResponseEntity.noContent().build();
    }
}
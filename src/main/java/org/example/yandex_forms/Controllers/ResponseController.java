package org.example.yandex_forms.Controllers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.yandex_forms.DTO.Response_DTO.SubmitResponseRequest;
import org.example.yandex_forms.Entityes.User;
import org.example.yandex_forms.Repositoryes.UserRepository;
import org.example.yandex_forms.Services.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    @PostMapping("/{formId}/responses")
    public ResponseEntity<Void> submitResponse(@PathVariable Long formId,
                                               @Valid @RequestBody SubmitResponseRequest request) {
        Long userId = getCurrentUserId();
        responseService.submitResponse(userId, formId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
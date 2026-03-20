package org.example.yandex_forms.Exception;

public class RequiredQuestionMissingException extends RuntimeException {
    public RequiredQuestionMissingException(String message) {
        super(message);
    }
}
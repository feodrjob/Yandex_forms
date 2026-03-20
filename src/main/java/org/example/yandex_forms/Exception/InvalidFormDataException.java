package org.example.yandex_forms.Exception;

public class InvalidFormDataException extends RuntimeException {
    public InvalidFormDataException(String message) {
        super(message);
    }
}
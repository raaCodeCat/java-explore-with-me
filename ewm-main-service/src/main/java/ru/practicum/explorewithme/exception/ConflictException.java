package ru.practicum.explorewithme.exception;

public class ConflictException extends BaseApiException {
    public ConflictException(String message) {
        super(message, "Нарушено ограничение целостности");
    }
}

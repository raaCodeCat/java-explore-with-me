package ru.practicum.explorewithme.exception;

public class NotFoundException extends BaseApiException {
    public NotFoundException(String message) {
        super(message, "Запрашиваемый объект не найден");
    }
}

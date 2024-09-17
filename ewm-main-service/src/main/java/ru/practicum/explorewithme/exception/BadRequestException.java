package ru.practicum.explorewithme.exception;

public class BadRequestException extends BaseApiException {
    public BadRequestException(String message) {
        super(message, "Ошибка при валидации входящих параметров");
    }
}

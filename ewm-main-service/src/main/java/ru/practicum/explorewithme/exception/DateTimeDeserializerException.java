package ru.practicum.explorewithme.exception;

public class DateTimeDeserializerException extends BaseApiException {
    public DateTimeDeserializerException(String message) {
        super(message, "Ошибка при валидации входящих параметров");
    }
}

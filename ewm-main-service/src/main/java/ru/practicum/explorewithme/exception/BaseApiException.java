package ru.practicum.explorewithme.exception;

import lombok.Getter;

@Getter
public class BaseApiException extends RuntimeException {
    private final String exceptionReason;

    public BaseApiException(String message, String reason) {
        super(message);
        this.exceptionReason = reason;
    }

    public BaseApiException(String message) {
        super(message);
        this.exceptionReason = null;
    }
}

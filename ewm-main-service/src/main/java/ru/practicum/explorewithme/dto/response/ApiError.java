package ru.practicum.explorewithme.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Сообщение об ошибке в API.
 */
@Getter
public class ApiError {
    private int code;
    private String status;
    private String message;
    private String reason;
    private Object errors;
    private LocalDateTime timestamp;

    private ApiError() {
    }

    public ApiError(HttpStatus httpStatus, String message, String reason, Object errors) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = httpStatus.name();
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message, Object errors) {
        this.errors = errors;
        this.message = message;
        this.reason = null;
        this.status = httpStatus.name();
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message, String reason) {
        this.errors = null;
        this.message = message;
        this.reason = reason;
        this.status = httpStatus.name();
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
    }
}



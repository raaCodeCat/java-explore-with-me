package ru.practicum.explorewithme.interseptor.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.explorewithme.dto.response.ApiError;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.DateTimeDeserializerException;
import ru.practicum.explorewithme.exception.NotFoundException;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice("ru.practicum.explorewithme")
@Slf4j
public class ErrorHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiError handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info("Ошибка 400: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                "Ошибка при валидации входящих параметров",
                List.of(exception.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeDeserializerException.class)
    public ApiError handleLocalDateTimeDeserializerExceptions(DateTimeDeserializerException exception) {
        log.info("Ошибка 400: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception.getExceptionReason(),
                List.of(exception.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiError handleConstraintViolationException(ConstraintViolationException exception) {
        log.info("Ошибка 400: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                "Ошибка при валидации входящих параметров",
                List.of(exception.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public ApiError handleConflictExceptions(ConflictException exception) {
        log.info("Ошибка 409: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                exception.getExceptionReason(),
                List.of(exception.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleNotFoundExceptions(NotFoundException exception) {
        log.info("Ошибка 404: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getExceptionReason(),
                List.of(exception.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiError handleMethodArgumentTypeMismatchExceptions(
            MethodArgumentTypeMismatchException exception) {
        Class<?> type = exception.getRequiredType();

        assert type != null;

        String message = String.format("В параметре %s ожидается тип данных %s", exception.getName(), type.getName());

        log.info("Ошибка 400: {}", message);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                "Ошибка при валидации входящих параметров",
                List.of(message)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handlePreValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            log.info("Ошибка 400: {}: {}", fieldName, errorMessage);
            errors.put(fieldName, errorMessage);
        });

        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Не правильно указаны параметры запроса",
                "Ошибка при валидации входящих параметров",
                errors
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiError handleInternalServerError(Exception exception) {
        log.info("Ошибка 500: {}", exception.getMessage());

        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                "Внутренняя ошибка сервера"
        );
    }
}

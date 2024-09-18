package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Данные для добавления нового события.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Данные для добавления нового события")
public class EventCreateDto {
    /**
     * Краткое описание события.
     */
    @NotBlank(message = "Краткое описание события не может быть пустым")
    @Length(min = 20, max = 2000, message = "Краткое описание события должно быть длинной")
    @Schema(description = "Краткое описание события")
    private String annotation;

    /**
     * Идентификатор категории, к которой относится событие.
     */
    @NotNull(message = "Идентификатор категории не может быть пустым")
    @Schema(description = "Идентификатор категории, к которой относится событие", example = "2")
    private Long category;

    /**
     * Полное описание события.
     */
    @NotBlank(message = "Полное описание события не может быть пустым")
    @Length(min = 20, max = 7000, message = "Полное описание события должно быть длинной от {min} до {max} символов")
    @Schema(description = "Полное описание события")
    private String description;

    /**
     * Дата и время, на которые намечено событие.
     */
    @NotNull(message = "Параметр не может быть пустым")
    @Schema(description = "Дата и время, на которые намечено событие", example = "2024-07-30 20:30:00")
    @Future(message = "Параметр должен содержать дату, которая еще не наступила")
    private LocalDateTime eventDate;

    /**
     * Широта и долгота места проведения события.
     */
    @NotNull
    @Schema(description = "Широта и долгота места проведения события")
    private LocationDto location;

    /**
     * Признак необходимости оплаты участия в событии.
     */
    @Schema(description = "Признак необходимости оплаты участия в событии", example = "true", defaultValue = "false")
    private Boolean paid = false;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения.
     */
    @Schema(description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            example = "0")
    @PositiveOrZero
    private Integer participantLimit = 0;

    /**
     * Признак необходимости пре-модерации заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    @Schema(description = "Признак необходимости пре-модерации заявок на участие", example = "true",
            defaultValue = "true")
    private Boolean requestModeration = true;

    /**
     * Заголовок события.
     */
    @NotBlank
    @Length(min = 3, max = 120, message = "Параметр должен быть длинной от {min} до {max}")
    @Schema(description = "Заголовок события")
    private String title;
}

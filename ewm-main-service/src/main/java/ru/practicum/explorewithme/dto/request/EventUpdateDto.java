package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ru.practicum.explorewithme.enums.EventStateAction;

import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

/**
 * Данные для обновления события.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Данные для обновления события")
public class EventUpdateDto {
    /**
     * Краткое описание события.
     */
    @Length(min = 20, max = 2000, message = "Краткое описание события должно быть длинной")
    @Schema(description = "Краткое описание события")
    private String annotation;

    /**
     * Идентификатор категории, к которой относится событие.
     */
    @Schema(description = "Идентификатор категории, к которой относится событие", example = "2")
    private Long category;

    /**
     * Полное описание события.
     */
    @Length(min = 20, max = 7000, message = "Полное описание события должно быть длинной от {min} до {max} символов")
    @Schema(description = "Полное описание события")
    private String description;

    /**
     * Дата и время, на которые намечено событие.
     */
    @Schema(description = "Дата и время, на которые намечено событие", example = "2024-07-30 20:30:00")
    @Future(message = "Параметр должен содержать дату, которая еще не наступила")
    private LocalDateTime eventDate;

    /**
     * Широта и долгота места проведения события.
     */
    @Schema(description = "Широта и долгота места проведения события")
    private LocationDto location;

    /**
     * Признак необходимости оплаты участия в событии.
     */
    @Schema(description = "Признак необходимости оплаты участия в событии", example = "true")
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения.
     */
    @Schema(description = "Ограничение на количество участников. Значение 0 - означает отсутствие ограничения",
            example = "0")
    @PositiveOrZero
    private Integer participantLimit;

    /**
     * Признак необходимости пре-модерации заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    @Schema(description = "Признак необходимости пре-модерации заявок на участие", example = "true")
    private Boolean requestModeration;

    /**
     * Изменение состояния события.
     */
    @Schema(description = "Изменение состояния события (SEND_TO_REVIEW или CANCEL_REVIEW)")
    private EventStateAction stateAction;

    /**
     * Заголовок события.
     */
    @Length(min = 3, max = 120, message = "Параметр должен быть длинной от {min} до {max}")
    @Schema(description = "Заголовок события")
    private String title;
}

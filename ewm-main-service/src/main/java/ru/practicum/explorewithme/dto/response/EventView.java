package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.enums.EventState;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Информация о событии.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о событии")
public class EventView {
    /**
     * Идентификатор.
     */
    @Schema(description = "Идентификатор события", example = "100")
    private Long id;

    /**
     * Заголовок события.
     */
    @Schema(description = "Заголовок события")
    private String title;

    /**
     * Краткое описание события.
     */
    @Schema(description = "Краткое описание события")
    private String annotation;

    /**
     * Категория события.
     */
    @Schema(description = "Категория события")
    private CategoryView category;

    /**
     * Количество одобренных заявок на участие в данном событии.
     */
    @Schema(description = "Количество одобренных заявок на участие в данном событии", example = "100")
    private Integer confirmedRequests;

    /**
     * Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss").
     */
    @Schema(description = "Дата и время создания события (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2024-07-30 20:30:00")
    private LocalDateTime createdOn;

    /**
     * Полное описание события.
     */
    @Schema(description = "Полное описание события")
    private String description;

    /**
     * Дата и время, на которые намечено событие.
     */
    @Schema(description = "Дата и время, на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2024-07-30 20:30:00")
    private LocalDateTime eventDate;

    /**
     * Пользователь (краткая информация).
     */
    @Schema(description = "Пользователь (краткая информация)")
    private UserShortView initiator;

    /**
     * Широта и долгота места проведения события.
     */
    @NotNull
    @Schema(description = "Широта и долгота места проведения события")
    private LocationView location;

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
    private Integer participantLimit;

    /**
     * Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss").
     */
    @Schema(description = "Дата и время публикации события (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2024-07-30 20:30:00")
    private LocalDateTime publishedOn;

    /**
     * Признак необходимости пре-модерации заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    @Schema(description = "Признак необходимости пре-модерации заявок на участие", example = "true")
    private Boolean requestModeration;

    /**
     * Состояние жизненного цикла заявки.
     */
    @Schema(description = "Состояние жизненного цикла заявки", example = "PUBLISHED")
    private EventState state;

    /**
     * Количество просмотрев события.
     */
    @Schema(description = "Количество просмотрев события", example = "100")
    private Long views;
}

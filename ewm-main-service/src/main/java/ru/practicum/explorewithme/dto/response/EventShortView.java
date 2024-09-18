package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Краткая информация о событии.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая информация о событии")
public class EventShortView {
    /**
     * Идентификатор.
     */
    @Schema(description = "Краткое описание события", example = "100")
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
    @Schema(description = "Категория")
    private CategoryView category;

    /**
     * Количество одобренных заявок на участие в данном событии.
     */
    @Schema(description = "Количество одобренных заявок на участие в данном событии", example = "100")
    private Integer confirmedRequests;

    /**
     * Дата и время, на которые намечено событие.
     */
    @Schema(description = "Дата и время, на которые намечено событие", example = "2024-07-30 20:30:00")
    private LocalDateTime eventDate;

    /**
     * Пользователь (краткая информация).
     */
    @Schema(description = "Пользователь (краткая информация)")
    private UserShortView initiator;

    /**
     * Признак необходимости оплаты участия в событии.
     */
    @Schema(description = "Признак необходимости оплаты участия в событии", example = "true")
    private Boolean paid;

    /**
     * Количество просмотрев события.
     */
    @Schema(description = "Количество просмотрев события", example = "100")
    private Long views;

    /**
     * Количество комментариев.
     */
    @Schema(description = "Количество комментариев", example = "100")
    private Long commentsCount;
}

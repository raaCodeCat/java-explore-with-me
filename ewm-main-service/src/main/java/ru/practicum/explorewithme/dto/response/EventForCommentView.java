package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Краткая информация о событии для отображения в комментарии.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая информация о событии для отображения в комментарии")
public class EventForCommentView {
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
     * Дата и время, на которые намечено событие.
     */
    @Schema(description = "Дата и время, на которые намечено событие", example = "2024-07-30 20:30:00")
    private LocalDateTime eventDate;
}

package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Комментарий к событию.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Комментарий к событию")
public class CommentView {
    /**
     * Идентификатор комментария.
     */
    @Schema(description = "Идентификатор комментария")
    private Long id;

    /**
     * Имя пользователь, написавшего комментарий.
     */
    @Schema(description = "Имя пользователь, написавшего комментарий")
    private UserShortView author;

    /**
     * Текст комментария.
     */
    @Schema(description = "Текст комментария")
    private String body;

    /**
     * Дата создания комментария.
     */
    @Schema(description = "Дата создания комментария")
    private LocalDateTime created;

    /**
     * Краткая информация о событии, на которое добавлен комментарий.
     */
    @Schema(description = "Краткая информация о событии, на которое добавлен комментарий")
    private EventForCommentView event;

    /**
     * Реакции на комментарий.
     */
    private CommentReactionsView reactions;
}

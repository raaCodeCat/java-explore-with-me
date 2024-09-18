package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Реакции на комментарий.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Реакции на комментарий")
public class CommentReactionsView {
    /**
     * Количество лайков.
     */
    private Long likeCount;

    /**
     * Количество дизлайков.
     */
    private Long dislikeCount;
}

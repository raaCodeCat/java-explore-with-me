package ru.practicum.explorewithme.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReactionsView {
    private Long likeCount;

    private Long dislikeCount;
}

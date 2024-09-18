package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.dto.response.CommentView;
import ru.practicum.explorewithme.enums.ReactionType;

import java.util.List;

/**
 * Сервис для работы с комментариями.
 */
public interface CommentService {
    CommentShortView createComment(Long eventId, CommentCreateDto createDto);

    CommentShortView getCommentById(Long commentId);

    List<CommentShortView> getCommentsByEventId(Long eventId, int from, int size);

    void deleteCommentById(Long commentId, Long userId);

    String addReactionToComment(Long commentId, Long userId, ReactionType reactionType);

    void deleteReactionFromComment(Long commentId, Long userId);

    List<CommentView> getCommentsByUserId(Long userId, int from, int size);
}

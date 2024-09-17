package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;

import java.util.List;

/**
 * Сервис для работы с комментариями.
 */
public interface CommentService {
    CommentShortView createComment(CommentCreateDto createDto);

    CommentShortView getCommentById(Long commentId);

    List<CommentShortView> getCommentsByEventId(Long eventId, int from, int size);

    void deleteCommentById(Long commentId);
}

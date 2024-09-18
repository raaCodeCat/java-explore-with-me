package ru.practicum.explorewithme.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.dto.response.CommentView;
import ru.practicum.explorewithme.enums.ReactionType;
import ru.practicum.explorewithme.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    /**
     * Добавление комментария к событию.
     */
    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentShortView addCommentToEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid CommentCreateDto createDto
    ) {
        return commentService.createComment(eventId, createDto);
    }

    /**
     * Получение комментария по идентификатору.
     */
    @GetMapping("/{commentId}")
    public CommentShortView getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    /**
     * Получение комментариев события.
     */
    @GetMapping("/events/{eventId}")
    public List<CommentShortView> getEventComments(
            @PathVariable Long eventId,
            @RequestParam
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 1000, message = "Параметр size не может быть больше {value}") int size
    ) {
        return commentService.getCommentsByEventId(eventId, from, size);
    }

    /**
     * Получения списка комментариев пользователя.
     */
    @GetMapping("/users/{userId}")
    public List<CommentView> getUserComments(
            @PathVariable Long userId,
            @RequestParam
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 1000, message = "Параметр size не может быть больше {value}") int size
    ) {
        return commentService.getCommentsByUserId(userId, from, size);
    }

    @DeleteMapping("/{commentId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        commentService.deleteCommentById(commentId, userId);
    }

    /**
     * Добавление лайка комментарию.
     */
    @PostMapping("{commentId}/users/{userId}/like")
    public Map<String, String> setLikeToComment(
            @PathVariable Long commentId,
            @PathVariable Long userId
    ) {
        return Map.of("result", commentService.addReactionToComment(commentId, userId, ReactionType.LIKE));
    }

    /**
     * Добавление дизлайка комментарию.
     */
    @PostMapping("{commentId}/users/{userId}/dislike")
    public Map<String, String> setDislikeToComment(
            @PathVariable Long commentId,
            @PathVariable Long userId
    ) {
        return Map.of("result", commentService.addReactionToComment(commentId, userId, ReactionType.DISLIKE));
    }

    /**
     * Удаление реакции (лайка/дизлайка) у комментария.
     */
    @DeleteMapping("{commentId}/users/{userId}/deleteReaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReactionFromComment(
            @PathVariable Long commentId,
            @PathVariable Long userId
    ) {
        commentService.deleteReactionFromComment(commentId, userId);
    }
}

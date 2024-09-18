package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.dto.response.CommentView;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.enums.ReactionType;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.CommentReaction;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CommentReactionRepository;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с комментариями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final EntityGettingServiceImpl entityGettingService;

    private final CommentRepository commentRepository;

    private final CommentReactionRepository commentReactionRepository;

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentShortView createComment(Long eventId, CommentCreateDto createDto) {
        log.info("Добавление комментария {}", createDto);
        Event event = entityGettingService.getEventById(eventId);

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Невозможно добавить комментарий к неопубликованному событию");
        }

        User user = entityGettingService.getUserById(createDto.getAuthor());
        Comment commentForCreate = commentMapper.convert(createDto);
        commentForCreate.setAuthor(user);
        commentForCreate.setEvent(event);
        commentForCreate.setCreated(LocalDateTime.now());

        try {
            Comment comment = commentRepository.save(commentForCreate);
            log.info("Комментарий добавлен");

            return commentMapper.convertShort(comment);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public CommentShortView getCommentById(Long commentId) {
        log.info("Запрос комментария по идентификатору {}", commentId);
        Comment comment = entityGettingService.getCommentById(commentId);

        return commentMapper.convertShort(comment);
    }

    @Override
    public List<CommentShortView> getCommentsByEventId(Long eventId, int from, int size) {
        log.info("Запрос комментариев по идентификатору события {}", eventId);
        Event event = entityGettingService.getEventById(eventId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.getCommentsByEventId(event.getId(), pageable);

        return commentMapper.convertShort(comments);
    }

    @Override
    public List<CommentView> getCommentsByUserId(Long userId, int from, int size) {
        log.info("Запрос комментариев по идентификатору пользователя {}", userId);
        User user = entityGettingService.getUserById(userId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.getCommentsByUserId(user.getId(), pageable);

        return commentMapper.convert(comments);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId, Long userId) {
        log.info("Удаление комментария с идентификатором {} пользователем с идентификатором {}",
                commentId, userId);
        Comment comment = entityGettingService.getCommentById(commentId);
        User user = entityGettingService.getUserById(userId);

        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException(
                    String.format("Пользователь с идентификатором %d не является автором " +
                            "комментария с идентификатором %d", userId, commentId),
                    "Удаление комментария не возможно");
        }

        commentRepository.delete(comment);
        log.info("Комментарий с идентификатором {} удален", commentId);
    }

    @Override
    @Transactional
    public String addReactionToComment(Long commentId, Long userId, ReactionType reactionType) {
        log.info("Добавление/изменение реакции на комментарий с идентификатором {} пользователем " +
                        "с идентификатором {}. Тип реакции {}",
                commentId, userId, reactionType);
        Comment comment = entityGettingService.getCommentById(commentId);
        User user = entityGettingService.getUserById(userId);
        CommentReaction commentReaction = commentReactionRepository
                .getCommentReactionByCommentIdAndUserId(commentId, userId).orElse(null);

        if (commentReaction == null) {
            CommentReaction newCommentReaction = new CommentReaction();
            newCommentReaction.setComment(comment);
            newCommentReaction.setUser(user);
            newCommentReaction.setReactionType(reactionType);
            commentReactionRepository.save(newCommentReaction);
            log.info(reactionType.getName() + " добавлен");

            return reactionType.getName() + " добавлен";
        } else {
            if (!commentReaction.getReactionType().equals(reactionType)) {
                ReactionType oldReaction = commentReaction.getReactionType();
                commentReaction.setReactionType(reactionType);
                commentReactionRepository.save(commentReaction);
                log.info(oldReaction.getName() + " заменён на " + reactionType.getName());

                return oldReaction.getName() + " заменён на " + reactionType.getName();
            } else {
                log.info(reactionType.getName() + " уже добавлен");

                return reactionType.getName() + " уже добавлен";
            }
        }
    }

    @Override
    @Transactional
    public void deleteReactionFromComment(Long commentId, Long userId) {
        log.info("Удаление реакции на комментарий {} пользователем {}",
                commentId, userId);
        Comment comment = entityGettingService.getCommentById(commentId);
        User user = entityGettingService.getUserById(userId);
        Optional<CommentReaction> commentReaction = commentReactionRepository
                .getCommentReactionByCommentIdAndUserId(comment.getId(), user.getId());

        commentReaction.ifPresent(commentReactionRepository::delete);
    }
}

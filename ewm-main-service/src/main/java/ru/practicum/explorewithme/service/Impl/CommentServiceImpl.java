package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с комментариями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final EntityGettingServiceImpl entityGettingService;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentShortView createComment(CommentCreateDto createDto) {
        log.info("Добавление комментария {}", createDto);
        Event event = entityGettingService.getEventById(createDto.getEvent());

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

            return commentMapper.convert(comment);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public CommentShortView getCommentById(Long commentId) {
        log.info("Запрос комментария по идентификатору {}", commentId);
        Comment comment = entityGettingService.getCommentById(commentId);

        return commentMapper.convert(comment);
    }

    @Override
    public List<CommentShortView> getCommentsByEventId(Long eventId, int from, int size) {
        log.info("Запрос комментариев по идентификатору события {}", eventId);
        Event event = entityGettingService.getEventById(eventId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.getCommentsByEventId(event.getId(), pageable);

        return commentMapper.convert(comments);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        log.info("Удаление комментария с идентификатором {}", commentId);
        Comment comment = entityGettingService.getCommentById(commentId);

        commentRepository.delete(comment);
        log.info("Комментарий с идентификатором {} удален", commentId);
    }
}

package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByEventId(Long eventId, Pageable pageable);
}

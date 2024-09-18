package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByEventId(Long eventId, Pageable pageable);

    @Query("select c " +
            "from Comment as c " +
            "inner join c.author as u " +
            "inner join fetch c.event as e " +
            "where u.id = :userId")
    List<Comment> getCommentsByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );
}

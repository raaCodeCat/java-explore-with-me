package ru.practicum.explorewithme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

/**
 * Комментарии к событиям.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    /**
     * Пользователь, написавший комментарий.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    /**
     * Событие, к которому написан комментарий.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * Текст комментария.
     */
    @Column(name = "comment_text")
    private String body;

    /**
     * Дата создания комментария.
     */
    @Column(name = "request_create_dt")
    private LocalDateTime created;

    /**
     * Количество лайков.
     */
    @Formula("(select coalesce(count(*),0) from commentreactions as cr where cr.comment_id = comment_id and cr.commentreaction_type = 'LIKE')")
    private Long likeCount = 0L;

    /**
     * Количество дизлайков.
     */
    @Formula("(select count(*) from commentreactions as cr where cr.comment_id = comment_id and cr.commentreaction_type = 'DISLIKE')")
    private Long dislikeCount = 0L;
}

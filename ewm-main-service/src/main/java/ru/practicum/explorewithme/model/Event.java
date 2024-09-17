package ru.practicum.explorewithme.model;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import ru.practicum.explorewithme.enums.EventState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * События.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@EqualsAndHashCode
public class Event {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    /**
     * Пользователь создавший событие.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User initiator;

    /**
     * Категория события.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Заголовок события.
     */
    @Column(name = "event_title")
    private String title;

    /**
     * Краткое описание события.
     */
    @Column(name = "event_annotation")
    private String annotation;

    /**
     * Полное описание события.
     */
    @Column(name = "event_description")
    private String description;

    /**
     * Дата и время, на которые намечено событие.
     */
    @Column(name = "event_datetime")
    private LocalDateTime eventDate;

    /**
     * Признак необходимости оплаты участия в событии.
     */
    @Column(name = "event_is_paid")
    private Boolean paid;

    /**
     * Признак необходимости пре-модерации заявок на участие.
     * Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    @Column(name = "event_is_request_moderation")
    private Boolean requestModeration;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения.
     */
    @Column(name = "event_participant_limit")
    private Integer participantLimit;

    /**
     * Широта.
     */
    @Column(name = "event_location_lat")
    private Float lat;

    /**
     * Долгота.
     */
    @Column(name = "event_location_lon")
    private Float lon;

    /**
     * Состояние жизненного цикла события.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    private EventState state;

    /**
     * Дота создания.
     */
    @Column(name = "event_create_dt")
    private LocalDateTime createdOn;

    /**
     * Дота публикации.
     */
    @Column(name = "event_publication_dt")
    private LocalDateTime publishedOn;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private List<Compilation> compilations = new ArrayList<>();

    /**
     * Количество подтверждённых заявок на участие
     */
    @Formula("(select count(*) from requests r where r.event_id = event_id and r.request_status = 'CONFIRMED')")
    private long confirmedRequests;

    /**
     * Количество комментариев.
     */
    @Formula("(select count(*) from comments c where c.event_id = event_id)")
    private Long commentsCount = 0L;
}

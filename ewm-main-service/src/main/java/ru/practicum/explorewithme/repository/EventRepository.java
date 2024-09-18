package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsByInitiator(User user, Pageable pageable);

    @Query(value = "select e " +
            "from Event e " +
            "inner join e.category as c " +
            "where e.state = 'PUBLISHED' " +
            "and (e.annotation ilike :text or e.description ilike :text or :text is null) " +
            "and (c.id in :categories or :categories is null) " +
            "and (e.paid = :paid or :paid is null) " +
            "and (e.eventDate >= :rangeStart) " +
            "and (e.eventDate <= :rangeEnd or cast(:rangeEnd as timestamp) is null) " +
            "and (:getOnlyAvailable = true and (e.participantLimit = 0 or e.participantLimit < e.confirmedRequests) " +
            "   or :getOnlyAvailable is null) " +
            "")
    List<Event> getPublishedEventsByParams(
            @Param("text") String text,
            @Param("categories") List<Long> categories,
            @Param("paid") Boolean paid,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("getOnlyAvailable") Boolean getOnlyAvailable,
            Pageable pageable
    );

    @Query(value = "select e " +
            "from Event e " +
            "inner join e.category as c " +
            "inner join e.initiator as u " +
            "where " +
            "(u.id in :users or :users is null) " +
            "and (e.state in :states or :states is null) " +
            "and (c.id in :categories or :categories is null) " +
            "and (e.eventDate >= :rangeStart) " +
            "and (e.eventDate <= :rangeEnd or cast(:rangeEnd as timestamp) is null) " +
            "")
    List<Event> getEventsByParams(
            @Param("users") List<Long> users,
            @Param("states") List<EventState> states,
            @Param("categories") List<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable
    );

    @Query("select e " +
            "from Event as e " +
            "where e.state = 'PUBLISHED' " +
            "and e.id = :eventId")
    Optional<Event> getPublishedEventById(@Param("eventId") Long eventId);
}

package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByEventIdAndIdInOrderByIdAsc(Long eventId, List<Long> requestIds);

    @Query("select count(r) " +
            "from Request as r " +
            "inner join r.event as e " +
            "where " +
            "e.id = :eventId " +
            "and r.status = 'CONFIRMED'")
    Long getCountConfirmedRequestByEventId(@Param("eventId") Long eventId);
}

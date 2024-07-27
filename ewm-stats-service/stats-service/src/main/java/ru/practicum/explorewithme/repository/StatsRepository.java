package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {
    @Query("select new ru.practicum.explorewithme.dto.response.StatsView(h.app, h.uri, count(h.ip)) " +
            "from Hit as h " +
            "where " +
            "(h.uri in (:uris) or (:uris) is null)" +
            "and (h.created between :start and :end) " +
            "group by " +
            "h.app, " +
            "h.uri " +
            "order by count(h.ip) desc, h.uri asc")
    List<StatsView> getHitsStatistic(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("select new ru.practicum.explorewithme.dto.response.StatsView(h.app, h.uri, count(distinct h.ip)) " +
            "from Hit as h " +
            "where " +
            "(h.uri in (:uris) or (:uris) is null) " +
            "and (h.created between :start and :end) " +
            "group by " +
            "h.app, " +
            "h.uri " +
            "order by count(distinct h.ip) desc, h.uri asc")
    List<StatsView> getUniqueHitsStatistic(
            @Param("uris") List<String> uris,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}

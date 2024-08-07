package ru.practicum.explorewithme.service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.response.StatsView;
import ru.practicum.explorewithme.service.EventStatsService;
import ru.practicum.explorewithme.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с сервисом статистики.
 */
@Service
@RequiredArgsConstructor
public class EventStatsServiceImpl implements EventStatsService {
    @Value("${ewm.app.name}")
    private String appName;
    private final StatsClient statsClient;

    @Override
    public void saveHit(HttpServletRequest request, String appName) {
        statsClient.saveHit(request, appName);
    }

    @Override
    public Long getEventViewCount(LocalDateTime start, LocalDateTime end, String uri) {
        List<StatsView> stats = statsClient.getStats(
                start.format(DateTimeUtil.formatter), end.format(DateTimeUtil.formatter), List.of(uri), true);

        if (stats.size() > 0) {
            return stats.get(0).getHits();
        } else {
            return 0L;
        }
    }

    public Map<String, Long> getEventsViewStats(LocalDateTime start, LocalDateTime end, List<String> uris) {
        Map<String, Long> eventsViewStats = new HashMap<>();
        for (String uri : uris) {
            eventsViewStats.put(uri, 0L);
        }

        if (start == null) {
            return eventsViewStats;
        }

        List<StatsView> stats = statsClient.getStats(
                start.format(DateTimeUtil.formatter), end.format(DateTimeUtil.formatter), uris, true);

        for (StatsView sv : stats) {
            eventsViewStats.put(sv.getUri(), sv.getHits());
        }

        return eventsViewStats;
    }
}

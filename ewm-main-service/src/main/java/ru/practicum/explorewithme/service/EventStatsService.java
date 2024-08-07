package ru.practicum.explorewithme.service;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с сервисом статистики.
 */
public interface EventStatsService {
    void saveHit(HttpServletRequest request, String appName);

    Long getEventViewCount(LocalDateTime start, LocalDateTime end, String uri);

    Map<String, Long> getEventsViewStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}

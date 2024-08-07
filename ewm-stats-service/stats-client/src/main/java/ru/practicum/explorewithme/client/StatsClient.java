package ru.practicum.explorewithme.client;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.explorewithme.dto.response.StatsView;

import java.util.List;

public interface StatsClient {
    void saveHit(HttpServletRequest request, String appName);

    List<StatsView> getStats(String start, String end, List<String> uris, Boolean unique);
}

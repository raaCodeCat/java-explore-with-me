package ru.practicum.explorewithme.client;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StatsClient {
    void saveHit(HttpServletRequest request, String appName);

    ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique);
}

package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class EventsController {
    @Value("${ewm.app.name}")
    private String appName;
    private final StatsClient statsClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(HttpServletRequest request) {
        log.info("GET /events");

        statsClient.saveHit(request, appName);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void getEventById(HttpServletRequest request, @PathVariable Long id) {
        log.info("GET /events/{}", id);

        statsClient.saveHit(request, appName);
    }

    // Метод для проверки работы метода получения статистики по посещениям.
    // Для теста в продакшен не пойдет
    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getEventsHitsStatus(
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре start должна быть в формате yyyy-MM-dd HH:mm:ss") String start,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре end должна быть в формате yyyy-MM-dd HH:mm:ss")String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique
    ) {
        return statsClient.getStats(start, end, uris, unique);
    }
}

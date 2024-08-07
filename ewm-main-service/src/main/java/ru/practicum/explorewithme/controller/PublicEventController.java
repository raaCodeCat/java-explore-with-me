package ru.practicum.explorewithme.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.EventFilter;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.enums.EventSort;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для работы с событиями.
 */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortView> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре start должна быть в формате yyyy-MM-dd HH:mm:ss") String rangeStart,
            @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре end должна быть в формате yyyy-MM-dd HH:mm:ss")String rangeEnd,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        EventFilter filter = new EventFilter();
        filter.setText(text);
        filter.setCategories(categories);
        filter.setPaid(paid);
        filter.setRangeStart(rangeStart != null ? LocalDateTime.parse(rangeStart, DateTimeUtil.formatter) : null);
        filter.setRangeEnd(rangeEnd != null ? LocalDateTime.parse(rangeEnd, DateTimeUtil.formatter) : null);
        filter.setOnlyAvailable(onlyAvailable);
        filter.setSort(sort);
        filter.setFrom(from);
        filter.setSize(size);

        log.info("GET /events с параметрами {}", filter);

        return eventService.getEvents(request, filter);
    }

    @GetMapping("/{id}")
    public EventShortView getEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("GET /events/{}", id);

        return eventService.getEventById(request, id);
    }
}

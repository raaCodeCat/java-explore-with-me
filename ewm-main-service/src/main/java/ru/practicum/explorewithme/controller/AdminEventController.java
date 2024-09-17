package ru.practicum.explorewithme.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.AdminEventFilter;
import ru.practicum.explorewithme.dto.request.EventUpdateByAdminDto;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventView eventUpdateByAdmin(
            @PathVariable Long eventId,
            @RequestBody @Valid EventUpdateByAdminDto updateDto) {
        log.info("Получен запрос PATCH /admin/events/{} с телом {}", eventId, updateDto);

        return eventService.eventUpdateByAdmin(eventId, updateDto);
    }

    @GetMapping
    public List<EventView> getEventsForAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<EventState> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре start должна быть в формате yyyy-MM-dd HH:mm:ss") String rangeStart,
            @RequestParam(required = false) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
                    message = "Дата в параметре end должна быть в формате yyyy-MM-dd HH:mm:ss") String rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        AdminEventFilter filter = new AdminEventFilter();
        filter.setUsers(users);
        filter.setStates(states);
        filter.setCategories(categories);
        filter.setRangeStart(rangeStart != null ?
                LocalDateTime.parse(rangeStart, DateTimeUtil.formatter) : null);
        filter.setRangeEnd(rangeEnd != null ?
                LocalDateTime.parse(rangeEnd, DateTimeUtil.formatter) : null);
        filter.setFrom(from);
        filter.setSize(size);

        return eventService.getEventsForAdmin(filter);
    }
}

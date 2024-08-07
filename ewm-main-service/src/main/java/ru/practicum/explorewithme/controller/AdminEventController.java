package ru.practicum.explorewithme.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.EventUpdateByAdminDto;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.service.EventService;

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
}

package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.EventCreateDto;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateDto;
import ru.practicum.explorewithme.dto.request.EventUpdateDto;
import ru.practicum.explorewithme.dto.response.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.dto.response.RequestView;
import ru.practicum.explorewithme.service.EventService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import ru.practicum.explorewithme.service.RequestService;

import java.util.List;

/**
 * Контроллер для работы пользователя с событиями.
 */
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {
    private final EventService eventService;

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventView addEvent(
            @PathVariable Long userId,
            @RequestBody @Valid EventCreateDto createDto
    ) {
        log.info("Получен запрос POST /users/{}/events с телом {}", userId, createDto);

        return  eventService.createEvent(userId, createDto);
    }

    @GetMapping
    public List<EventShortView> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 100, message = "Параметр size не может быть больше {value}") int size
    ) {
        log.info("Получен запрос GET /users/{}/events?from={}&size={}", userId, from, size);

        return eventService.getUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventView getUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{}/events/{}", userId, eventId);

        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventView updateUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid EventUpdateDto updateDto
    ) {
        log.info("Получен запрос PATCH /users/{}/events/{} с телом {}", userId, eventId, updateDto);

        return eventService.updateEvent(userId, eventId, updateDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestView> getUserEventRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Получен запрос GET /users/{}/events/{}/requests", userId, eventId);

        return requestService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequestsStatus(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateDto updateDto
    ) {
        log.info("Получен запрос PATH /users/{}/events{}/requests с телом {}", userId, eventId,
                updateDto);

        return requestService.updateEventRequestsStatus(userId, eventId, updateDto);
    }
}

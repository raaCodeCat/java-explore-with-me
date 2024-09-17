package ru.practicum.explorewithme.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.explorewithme.dto.request.AdminEventFilter;
import ru.practicum.explorewithme.dto.request.EventUpdateByAdminDto;
import ru.practicum.explorewithme.dto.request.EventCreateDto;
import ru.practicum.explorewithme.dto.request.EventFilter;
import ru.practicum.explorewithme.dto.request.EventUpdateDto;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с событиями.
 */
public interface EventService {
    EventView createEvent(Long userId, EventCreateDto createDto);

    List<EventShortView> getUserEvents(Long userId, int from, int size);

    EventView getUserEventById(Long userId, Long eventId);

    EventView updateEvent(Long userId, Long eventId, EventUpdateDto updateDto);

    List<EventShortView> getEvents(HttpServletRequest request, EventFilter filter);

    EventView getEventById(HttpServletRequest request, Long eventId);

    EventView eventUpdateByAdmin(Long eventId, EventUpdateByAdminDto updateDto);

    List<EventView> getEventsForAdmin(AdminEventFilter filter);

    Map<String, Long> getEventsViewStats(List<Event> events);
}

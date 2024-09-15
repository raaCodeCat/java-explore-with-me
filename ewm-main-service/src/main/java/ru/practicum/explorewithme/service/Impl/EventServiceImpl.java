package ru.practicum.explorewithme.service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.EventUpdateByAdminDto;
import ru.practicum.explorewithme.dto.request.EventCreateDto;
import ru.practicum.explorewithme.dto.request.EventFilter;
import ru.practicum.explorewithme.dto.request.EventUpdateDto;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.enums.EventSort;
import ru.practicum.explorewithme.enums.EventState;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.EntityGettingService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.EventStatsService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для работы с событиями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EntityGettingService entityGettingService;

    private final EventStatsService statsService;

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private static final String PATH = "/events/";

    @Value("${ewm.app.name}")
    private String appName;

    @Override
    @Transactional
    public EventView createEvent(Long userId, EventCreateDto createDto) {
        log.info("Пользователь с идентификатором {} создает событие {}", userId, createDto);

        checkEventData(createDto.getEventDate());
        User initiator = entityGettingService.getUserById(userId);
        Category category = entityGettingService.getCategoryById(createDto.getCategory());
        Event eventForCreate = eventMapper.convert(createDto);
        eventForCreate.setInitiator(initiator);
        eventForCreate.setCategory(category);
        eventForCreate.setState(EventState.PENDING);
        eventForCreate.setCreatedOn(LocalDateTime.now());

        Event event = eventRepository.save(eventForCreate);
        log.info("Событие создано {}", event);

        return eventMapper.convert(event, 0L);
    }

    @Override
    public List<EventShortView> getUserEvents(Long userId, int from, int size) {
        log.info("Получение списка событий созданных пользователем с идентификатором {} с параметрами from={}, size={}",
                userId, from, size);

        User user = entityGettingService.getUserById(userId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Event> events = eventRepository.getEventsByInitiator(user, pageable);
        Map<String, Long> stats = getEventsViewStats(events);
        List<EventShortView> eventShortViews = eventMapper.convertShort(events);

        for (EventShortView eventShortView : eventShortViews) {
            eventShortView.setViews(stats.getOrDefault(PATH + eventShortView.getId(), 0L));
        }

        return eventShortViews;
    }

    @Override
    public EventView getUserEventById(Long userId, Long eventId) {
        log.info("Получение события с идентификатором {} пользователем с идентификатором {}}",eventId, userId);

        User user = entityGettingService.getUserById(userId);
        Event event = entityGettingService.getEventById(eventId);
        checkUserIsInitiatorOfEvent(user, event);

        if (event.getState().equals(EventState.PUBLISHED)) {
            Long viewCount = statsService.getEventViewCount(
                    event.getPublishedOn(),
                    LocalDateTime.now(),
                    PATH + eventId
            );

            return eventMapper.convert(event, viewCount);
        } else {
            return eventMapper.convert(event, 0L);
        }
    }

    @Override
    @Transactional
    public EventView updateEvent(Long userId, Long eventId, EventUpdateDto updateDto) {
        log.info("Обновление события с идентификатором {} пользователем с идентификатором {}. Новые данные {}",
                eventId, userId, updateDto);
        User user = entityGettingService.getUserById(userId);
        Event eventForUpdate = entityGettingService.getEventById(eventId);
        checkUserIsInitiatorOfEvent(user, eventForUpdate);
        checkEventIsNotPublished(eventForUpdate);

        if (updateDto.getAnnotation() != null) {
            eventForUpdate.setAnnotation(updateDto.getAnnotation());
        }

        if (updateDto.getCategory() != null) {
            Category category = entityGettingService.getCategoryById(updateDto.getCategory());
            eventForUpdate.setCategory(category);
        }

        if (updateDto.getDescription() != null) {
            eventForUpdate.setDescription(updateDto.getDescription());
        }

        if (updateDto.getEventDate() != null) {
            checkEventData(updateDto.getEventDate());
            eventForUpdate.setEventDate(updateDto.getEventDate());
        }

        if (updateDto.getLocation() != null && updateDto.getLocation().getLon() != null) {
            eventForUpdate.setLon(updateDto.getLocation().getLon());
        }

        if (updateDto.getLocation() != null && updateDto.getLocation().getLat() != null) {
            eventForUpdate.setLat(updateDto.getLocation().getLat());
        }

        if (updateDto.getPaid() != null) {
            eventForUpdate.setPaid(updateDto.getPaid());
        }

        if (updateDto.getParticipantLimit() != null) {
            eventForUpdate.setParticipantLimit(updateDto.getParticipantLimit());
        }

        if (updateDto.getRequestModeration() != null) {
            eventForUpdate.setRequestModeration(updateDto.getRequestModeration());
        }

        if (updateDto.getTitle() != null) {
            eventForUpdate.setTitle(updateDto.getTitle());
        }

        if (updateDto.getStateAction() != null) {
            switch (updateDto.getStateAction()) {
                case CANCEL_REVIEW:
                    eventForUpdate.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    eventForUpdate.setState(EventState.PENDING);
            }
        }

        Event event = eventRepository.save(eventForUpdate);
        log.info("Событие обновлено");

        return eventMapper.convert(event, 0L);
    }

    @Override
    public List<EventShortView> getEvents(HttpServletRequest request, EventFilter filter) {
        statsService.saveHit(request, appName);
        String text = filter.getText() != null ? '%' + filter.getText() + '%' : null;
        List<Long> categories = filter.getCategories();
        Boolean paid = filter.getPaid();
        LocalDateTime rangeStart;
        LocalDateTime rangeEnd;

        if (filter.getRangeStart() == null && filter.getRangeEnd() == null) {
            rangeStart = LocalDateTime.now();
            rangeEnd = null;
        } else {
            rangeStart = filter.getRangeStart();
            rangeEnd = filter.getRangeEnd();
        }

        Boolean onlyAvailable = filter.getOnlyAvailable() != null ? (filter.getOnlyAvailable() ? true : null) : null;
        EventSort sort = filter.getSort();
        Integer from = filter.getFrom();
        Integer size = filter.getSize();

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Event> events = eventRepository.getPublishedEventsByParams(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, pageable);

        if (events.size() == 0) {
            return List.of();
        }

        Map<String, Long> stats = getEventsViewStats(events);
        List<EventShortView> eventShortViews = eventMapper.convertShort(events);

        for (EventShortView eventShortView : eventShortViews) {
            eventShortView.setViews(stats.getOrDefault(PATH + eventShortView.getId(), 0L));
        }

        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    return eventShortViews.stream()
                            .sorted(Comparator.comparing(EventShortView::getEventDate))
                            .collect(Collectors.toList());
                case VIEWS:
                    return eventShortViews.stream()
                            .sorted(Comparator.comparing(EventShortView::getViews))
                            .collect(Collectors.toList());
            }
        }

        return eventShortViews;
    }

    @Override
    @Transactional
    public EventShortView getEventById(HttpServletRequest request, Long eventId) {
        Event event = eventRepository.getPublishedEventById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с идентификатором %d не найдено", eventId)));
        statsService.saveHit(request, appName);

        Long viewCount = statsService.getEventViewCount(
                event.getPublishedOn(),
                LocalDateTime.now(),
                PATH + eventId
        );

        EventShortView eventShortView = eventMapper.convertShort(event);
        eventShortView.setViews(viewCount);

        return eventShortView;
    }

    private Map<String, Long> getEventsViewStats(List<Event> events) {
        if (events == null || events.size() == 0) {
            return Map.of();
        }

        List<String> uris = new ArrayList<>();
        LocalDateTime start = events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .min(Comparator.comparing(Event::getPublishedOn))
                .map(Event::getPublishedOn).orElse(null);

        for (Event event : events) {
            uris.add(PATH + event.getId());
        }

        return statsService.getEventsViewStats(start, LocalDateTime.now(), uris);
    }

    @Override
    public EventView eventUpdateByAdmin(Long eventId, EventUpdateByAdminDto updateDto) {
        log.info("Обновление события с идентификатором {} администратором. Новые данные {}",
                eventId, updateDto);
        Event eventForUpdate = entityGettingService.getEventById(eventId);
        Long viewCount = 0L;

        if (eventForUpdate.getState().equals(EventState.PUBLISHED)) {
            viewCount = statsService.getEventViewCount(
                    eventForUpdate.getPublishedOn(),
                    LocalDateTime.now(),
                    PATH + eventId
            );
        }

        if (updateDto.getAnnotation() != null) {
            eventForUpdate.setAnnotation(updateDto.getAnnotation());
        }

        if (updateDto.getCategory() != null) {
            Category category = entityGettingService.getCategoryById(updateDto.getCategory());
            eventForUpdate.setCategory(category);
        }

        if (updateDto.getDescription() != null) {
            eventForUpdate.setDescription(updateDto.getDescription());
        }

        if (updateDto.getEventDate() != null) {
            checkEventData(updateDto.getEventDate());
            eventForUpdate.setEventDate(updateDto.getEventDate());
        }

        if (updateDto.getLocation() != null && updateDto.getLocation().getLon() != null) {
            eventForUpdate.setLon(updateDto.getLocation().getLon());
        }

        if (updateDto.getLocation() != null && updateDto.getLocation().getLat() != null) {
            eventForUpdate.setLat(updateDto.getLocation().getLat());
        }

        if (updateDto.getPaid() != null) {
            eventForUpdate.setPaid(updateDto.getPaid());
        }

        if (updateDto.getParticipantLimit() != null) {
            eventForUpdate.setParticipantLimit(updateDto.getParticipantLimit());
        }

        if (updateDto.getRequestModeration() != null) {
            eventForUpdate.setRequestModeration(updateDto.getRequestModeration());
        }

        if (updateDto.getTitle() != null) {
            eventForUpdate.setTitle(updateDto.getTitle());
        }

        if (updateDto.getStateAction() != null) {
            switch (updateDto.getStateAction()) {
                case REJECT_EVENT:
                    checkEventIsNotPublished(eventForUpdate);
                    eventForUpdate.setState(EventState.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    checkEventIsPendingPublishing(eventForUpdate);
                    eventForUpdate.setState(EventState.PUBLISHED);
                    eventForUpdate.setPublishedOn(LocalDateTime.now());
            }
        }

        Event event = eventRepository.save(eventForUpdate);
        log.info("Событие обновлено");

        return eventMapper.convert(event, viewCount);
    }

    private void checkEventData(LocalDateTime eventDate) {
        Duration duration = Duration.between(LocalDateTime.now(), eventDate);

        if (duration.toMinutes() < 120) {
            throw new ConflictException("Дата и время на которые намечено событие не может быть раньше," +
                    " чем через два часа от текущего момента");
        }
    }

    private void checkUserIsInitiatorOfEvent(User user, Event event) {
        if (!event.getInitiator().equals(user)) {
            throw new NotFoundException(
                    String.format("Пользователю с идентификатором %d не доступно событие с идентификатором %d",
                            user.getId(), event.getInitiator().getId())
            );
        }
    }

    private void checkEventIsNotPublished(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(String.format("Событие с идентификатором %d уже опубликовано", event.getId()));
        }
    }

    private void checkEventIsPendingPublishing(Event event) {
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConflictException(String.format("Событие с идентификатором %d не ожидает публикации",
                    event.getId()));
        }
    }
}

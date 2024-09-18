package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.CompilationCreateDto;
import ru.practicum.explorewithme.dto.request.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.response.CompilationView;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.service.CompilationService;
import ru.practicum.explorewithme.service.EntityGettingService;
import ru.practicum.explorewithme.service.EventService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final EntityGettingService entityGettingService;

    private final EventService eventService;

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public CompilationView addCompilation(CompilationCreateDto createDto) {
        log.info("Добавление подборки событий {}", createDto);

        Map<String, Long> viewStats = new HashMap<>();

        try {
            Compilation compilation = compilationRepository.save(compilationMapper.convert(createDto));

            if (createDto.getEvents() != null) {
                List<Event> events = entityGettingService.getEventsByIds(createDto.getEvents());
                compilation.setEvents(events);
                viewStats = eventService.getEventsViewStats(events);
            }

            log.info("Подборка событий добавлена {}", createDto);

            CompilationView compilationView = compilationMapper.convert(compilation);

            addEventViewToCompilation(compilationView, viewStats);

            return compilationView;
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        log.info("Удаление подборки событий с идентификатором {}", compId);
        Compilation compilation = entityGettingService.getCompilationById(compId);

        if (compilation.getEvents().size() > 0) {
            throw new ConflictException("Удалить можно только пустую подборку");
        }

        compilationRepository.deleteById(compilation.getId());
        log.info("Подборки событий с идентификатором {} удалена", compId);
    }

    @Override
    @Transactional
    public CompilationView updateCompilation(Long compId, CompilationUpdateDto updateDto) {
        log.info("Обновление подборки событий новыми данными {}", updateDto);
        Compilation compForUpdate = entityGettingService.getCompilationById(compId);
        Map<String, Long> viewStats = new HashMap<>();

        if (updateDto.getTitle() != null) {
            compForUpdate.setTitle(updateDto.getTitle());
        }

        if (updateDto.getPinned() != null) {
            compForUpdate.setPinned(updateDto.getPinned());
        }

        if (updateDto.getEvents() != null) {
            compForUpdate.setEvents(updateDto.getEvents().stream()
                    .map(eventMapper::convert).collect(Collectors.toList()));
        }

        try {
            Compilation compilation = compilationRepository.save(compForUpdate);

            if (updateDto.getEvents() != null) {
                List<Event> events = entityGettingService.getEventsByIds(updateDto.getEvents());
                compilation.setEvents(events);
                viewStats = eventService.getEventsViewStats(events);
            }

            CompilationView compilationView = compilationMapper.convert(compilation);
            addEventViewToCompilation(compilationView, viewStats);

            log.info("Подборка событий обновлена {}", compilation);

            return compilationView;
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<CompilationView> getCompilations(Boolean pinned, int from, int size) {
        log.info("Получение списка подборок с параметрами pinned={}, from={}, size={}", pinned, from, size);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        List<Compilation> compilations;

        if (pinned != null) {
            compilations = compilationRepository.getCompilationByPinned(pinned, pageable);
        } else {
            compilations = compilationRepository.findAll(pageable).stream().collect(Collectors.toList());
        }

        List<Event> events = getEventList(compilations);
        Map<String, Long> viewStats = eventService.getEventsViewStats(events);

        return compilationMapper.convert(compilations).stream()
                .peek(c -> addEventViewToCompilation(c, viewStats))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationView getCompilationById(Long compId) {
        Compilation compilation = entityGettingService.getCompilationById(compId);
        Map<String, Long> viewStats = eventService.getEventsViewStats(compilation.getEvents());
        CompilationView compilationView = compilationMapper.convert(compilation);
        addEventViewToCompilation(compilationView, viewStats);

        return compilationView;
    }

    private List<Event> getEventList(List<Compilation> compilations) {
        Set<Event> eventSet = new HashSet<>();

        for (Compilation compilation : compilations) {
            if (compilation.getEvents() != null && compilation.getEvents().size() > 0) {
                eventSet.addAll(compilation.getEvents());
            }
        }

        return new ArrayList<>(eventSet);
    }

    private void addEventViewToCompilation(CompilationView compilation, Map<String, Long> viewStats) {
        if (compilation.getEvents() == null || compilation.getEvents().size() == 0) {
            return;
        }

        for (EventShortView event : compilation.getEvents()) {
            event.setViews(viewStats.getOrDefault("/events/" + event.getId(), 0L));
        }
    }
}

package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.CompilationCreateDto;
import ru.practicum.explorewithme.dto.request.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.response.CompilationView;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.service.CompilationService;
import ru.practicum.explorewithme.service.EntityGettingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final EntityGettingService entityGettingService;

    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public CompilationView addCompilation(CompilationCreateDto createDto) {
        log.info("Добавление подборки событий {}", createDto);

        try {
            Compilation compilation = compilationRepository.save(compilationMapper.convert(createDto));

            if (createDto.getEvents() != null) {
                List<Event> events = entityGettingService.getEventsByIds(createDto.getEvents());
                compilation.setEvents(events);
            }

            log.info("Подборка событий добавлена {}", createDto);

            return compilationMapper.convert(compilation);
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
    public CompilationView updateCompilation(Long compId, CompilationUpdateDto updateDto) {
        log.info("Обновление подборки событий новыми данными {}", updateDto);
        Compilation compForUpdate = entityGettingService.getCompilationById(compId);

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
            }

            log.info("Подборка событий обновлена {}", compilation);

            return compilationMapper.convert(compilation);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }
}

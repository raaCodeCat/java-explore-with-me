package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.EntityGettingService;

import java.util.List;

/**
 * Сервис для получения сущностей из БД.
 */
@Service
@RequiredArgsConstructor
public class EntityGettingServiceImpl implements EntityGettingService {
    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private final RequestRepository requestRepository;

    private final CompilationRepository compilationRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с идентификатором %d не найден", id)));
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Категория с идентификатором %d не найден", id)));
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Событие с идентификатором %d не найдено", id)));
    }

    public List<Event> getEventsByIds(List<Long> ids) {
        return eventRepository.findAllById(ids);
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Заявка с идентификатором %d не найдено", id)));
    }

    @Override
    public Compilation getCompilationById(Long id) {
        return compilationRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Подборка событий с идентификатором %d не найдено", id)));
    }
}

package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;

import java.util.List;

/**
 * Сервис для получения сущностей из БД.
 */
public interface EntityGettingService {
    User getUserById(Long id);

    Category getCategoryById(Long id);

    Event getEventById(Long id);

    List<Event> getEventsByIds(List<Long> ids);

    Request getRequestById(Long id);

    Compilation getCompilationById(Long id);
}

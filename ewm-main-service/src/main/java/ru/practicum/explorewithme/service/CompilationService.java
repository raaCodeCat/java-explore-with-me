package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.CompilationCreateDto;
import ru.practicum.explorewithme.dto.request.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.response.CompilationView;

import java.util.List;

/**
 * Сервис для работы с подборками событий.
 */
public interface CompilationService {
    CompilationView addCompilation(CompilationCreateDto createDto);

    void deleteCompilation(Long compId);

    CompilationView updateCompilation(Long compId, CompilationUpdateDto updateDto);

    List<CompilationView> getCompilations(Boolean pinned, int from, int size);

    CompilationView getCompilationById(Long compId);
}

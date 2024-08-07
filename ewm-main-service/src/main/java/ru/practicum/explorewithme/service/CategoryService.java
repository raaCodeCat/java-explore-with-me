package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.CategoryCreateDto;
import ru.practicum.explorewithme.dto.request.CategoryDto;
import ru.practicum.explorewithme.dto.response.CategoryView;

/**
 * Сервис для работы с категориями.
 */
public interface CategoryService {
    CategoryView create(CategoryCreateDto categoryCreateDto);

    CategoryView update(CategoryDto categoryDto);

    void deleteById(Long id);
}

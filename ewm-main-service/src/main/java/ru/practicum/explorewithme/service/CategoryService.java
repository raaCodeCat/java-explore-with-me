package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.request.CategoryCreateDto;
import ru.practicum.explorewithme.dto.request.CategoryDto;
import ru.practicum.explorewithme.dto.response.CategoryView;

import java.util.List;

/**
 * Сервис для работы с категориями.
 */
public interface CategoryService {
    CategoryView create(CategoryCreateDto categoryCreateDto);

    CategoryView update(CategoryDto categoryDto);

    void deleteById(Long id);

    List<CategoryView> getCategories(int from, int size);

    CategoryView getCategoryById(Long catId);
}

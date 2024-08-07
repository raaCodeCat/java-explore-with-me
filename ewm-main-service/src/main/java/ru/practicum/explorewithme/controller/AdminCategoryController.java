package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.request.CategoryDto;
import ru.practicum.explorewithme.dto.request.CategoryCreateDto;
import ru.practicum.explorewithme.dto.response.CategoryView;
import ru.practicum.explorewithme.service.CategoryService;

import jakarta.validation.Valid;

/**
 * Контроллер администратора для работы с категориями.
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryView addCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        log.info("Получен запрос POST /admin/categories c телом {}", categoryCreateDto);

        return categoryService.create(categoryCreateDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Получен запрос DELETE /admin/categories/{}", catId);

        categoryService.deleteById(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryView updateCategory(
            @PathVariable Long catId,
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.info("Получен запрос PATCH /admin/categories/{} с телом {}", catId, categoryDto);
        categoryDto.setId(catId);

        return categoryService.update(categoryDto);
    }


}

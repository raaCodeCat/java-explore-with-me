package ru.practicum.explorewithme.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.response.CategoryView;
import ru.practicum.explorewithme.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryView> getCategories(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}") int size
    ) {
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryView getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}

package ru.practicum.explorewithme.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.request.CategoryCreateDto;
import ru.practicum.explorewithme.dto.request.CategoryDto;
import ru.practicum.explorewithme.dto.response.CategoryView;
import ru.practicum.explorewithme.exception.ConflictException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.service.CategoryService;
import ru.practicum.explorewithme.service.EntityGettingService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с категориями.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final EntityGettingService entityGettingService;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryView create(CategoryCreateDto categoryCreateDto) {
        Category categoryForCreate = categoryMapper.convert(categoryCreateDto);
        log.info("Добавление категории {}", categoryForCreate);

        try {
            Category category = categoryRepository.save(categoryForCreate);
            log.info("Категория добавлена");

            return categoryMapper.convert(category);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    @Transactional
    public CategoryView update(CategoryDto categoryDto) {
        Category categoryForUpdate = entityGettingService.getCategoryById(categoryDto.getId());
        String newCategoryName = categoryDto.getName();
        log.info("Обновление категории {} данными {}", categoryForUpdate, categoryDto);

        if (categoryForUpdate.getName().equals(newCategoryName)) {
            log.info("Обновление не требуется");

            return categoryMapper.convert(categoryForUpdate);
        }

        categoryForUpdate.setName(newCategoryName);

        try {
            Category category = categoryRepository.save(categoryForUpdate);
            log.info("Категория обновлена");

            return categoryMapper.convert(category);
        }  catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Category category = entityGettingService.getCategoryById(id);

        log.info("Удаление категории {}", category);

        try {
            categoryRepository.delete(category);
            log.info("Категория удалена");
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<CategoryView> getCategories(int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        return categoryMapper.convert(categoryRepository.findAll(pageable).stream().collect(Collectors.toList()));
    }

    @Override
    public CategoryView getCategoryById(Long catId) {
        Category category = entityGettingService.getCategoryById(catId);

        return categoryMapper.convert(category);
    }
}

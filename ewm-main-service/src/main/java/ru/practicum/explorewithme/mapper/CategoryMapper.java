package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.request.CategoryDto;
import ru.practicum.explorewithme.dto.request.CategoryCreateDto;
import ru.practicum.explorewithme.dto.response.CategoryView;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category convert(CategoryCreateDto source);

    Category convert(CategoryDto source);

    CategoryView convert(Category source);

    List<CategoryView> convert(List<Category> source);

    Category getEntity(Long id);

    List<Category> getEntity(List<Long> ids);
}

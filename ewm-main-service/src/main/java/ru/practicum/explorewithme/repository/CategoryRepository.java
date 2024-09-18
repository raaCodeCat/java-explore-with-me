package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Category;

/**
 * Репозиторий для работы с категориями.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

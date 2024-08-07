package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Категория.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Категория")
public class CategoryView {
    /**
     * Идентификатор категории.
     */
    @Schema(description = "Идентификатор категории")
    private Long id;

    /**
     * Название категории.
     */
    @Schema(description = "Название категории")
    private String name;
}

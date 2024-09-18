package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * Категория.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Категория")
public class CategoryDto {
    /**
     * Идентификатор категории.
     */
    @Schema(description = "Идентификатор категории")
    private Long id;

    /**
     * Название категории.
     */
    @NotBlank(message = "Параметр не может быть пустым")
    @Length(min = 1, max = 50, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Название категории", example = "Концерты")
    private String name;
}

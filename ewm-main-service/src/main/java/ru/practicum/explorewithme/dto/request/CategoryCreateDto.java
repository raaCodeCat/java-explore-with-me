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
 * Данные для добавления новой категории.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления новой категории")
public class CategoryCreateDto {
    /**
     * Название категории.
     */
    @NotBlank(message = "Параметр не может быть пустым")
    @Length(min = 1, max = 50, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Название категории", example = "Концерты")
    private String name;
}

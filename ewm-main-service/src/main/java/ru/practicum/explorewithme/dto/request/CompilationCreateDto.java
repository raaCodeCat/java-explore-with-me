package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Данные для добавления новой подборки событий.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления новой подборки событий")
public class CompilationCreateDto {
    /**
     * Список идентификаторов событий входящих в подборку.
     */
    @Schema(description = "Список идентификаторов событий входящих в подборку", example = "[1, 2, 3]")
    private List<Long> events;

    /**
     * Признак закреплена ли подборка на главной странице сайта.
     */
    @Schema(description = "Закреплена ли подборка на главной странице сайта", example = "false")
    private Boolean pinned = false;

    /**
     * Заголовок подборки.
     */
    @NotBlank(message = "Параметр не может быть пустым")
    @Length(min = 1, max = 50, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Заголовок подборки", example = "Летние концерты")
    private String title;
}

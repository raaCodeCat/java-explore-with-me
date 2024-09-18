package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Данные для обновлений подборки событий.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для обновлений подборки событий")
public class CompilationUpdateDto {
    /**
     * Список идентификаторов событий для полной замены текущего списка.
     */
    @Schema(description = "Список идентификаторов событий для полной замены текущего списка", example = "[1, 2, 3]")
    private List<Long> events;

    /**
     * Признак закреплена ли подборка на главной странице сайта.
     */
    @Schema(description = "Закреплена ли подборка на главной странице сайта", example = "false")
    private Boolean pinned;

    /**
     * Заголовок подборки.
     */
    @Length(min = 1, max = 50, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Заголовок подборки", example = "Летние концерты")
    private String title;
}

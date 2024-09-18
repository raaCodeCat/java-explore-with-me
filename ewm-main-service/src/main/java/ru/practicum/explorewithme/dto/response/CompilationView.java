package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Подборка событий.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Подборка событий")
public class CompilationView {
    /**
     * Идентификатор подборки событий.
     */
    @Schema(description = "Идентификатор подборки событий")
    private Long id;

    /**
     * Список событий.
     */
    @Schema(description = "Список событий")
    private List<EventShortView> events;

    /**
     * Закреплена ли подборка на главной странице сайта.
     */
    @Schema(description = "Закреплена ли подборка на главной странице сайта")
    private Boolean pinned;

    /**
     * Заголовок подборки.
     */
    @Schema(description = "Заголовок подборки")
    private String title;
}

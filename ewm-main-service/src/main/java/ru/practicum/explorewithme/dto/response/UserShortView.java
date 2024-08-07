package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Пользователь (краткая информация).
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Пользователь (краткая информация)")
public class UserShortView {
    /**
     * Идентификатор пользователя.
     */
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String name;
}

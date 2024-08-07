package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Пользователь.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Пользователь")
public class UserView {
    /**
     * Идентификатор пользователя.
     */
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    /**
     * Адрес электронной почты.
     */
    @Schema(description = "Адрес электронной почты")
    private String email;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String name;
}

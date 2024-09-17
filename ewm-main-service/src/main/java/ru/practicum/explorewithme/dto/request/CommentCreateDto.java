package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * Комментарий к событию.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Комментарий к событию")
public class CommentCreateDto {
    /**
     * Идентификатор пользователя, написавшего комментарий.
     */
    @NotNull(message = "Параметр не может быть пустым")
    @Schema(description = "Идентификатор пользователя, написавшего комментарий", example = "1")
    private Long author;

    /**
     * Идентификатор события, к которому написан комментарий.
     */
    @NotNull(message = "Параметр не может быть пустым")
    @Schema(description = "Идентификатор события, к которому написан комментарий", example = "1")
    private Long event;

    /**
     * Текст комментария.
     */
    @NotNull(message = "Параметр не может быть пустым")
    @Length(min = 1, max = 300, message = "Параметр должен быть длинной от {min} до {max} символов")
    @Schema(description = "Текст комментария", example = "Событие интересное, но далеко ехать")
    private String body;
}

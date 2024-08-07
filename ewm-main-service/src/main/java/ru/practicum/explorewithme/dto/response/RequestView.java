package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Заявка на участие в событии.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Заявка на участие в событии")
public class RequestView {
    /**
     * Идентификатор заявки на участие в событии.
     */
    @Schema(description = "Идентификатор заявки на участие в событии", example = "100")
    private Long id;

    /**
     * Идентификатор события.
     */
    @Schema(description = "Идентификатор события", example = "1")
    private Long event;

    /**
     * Идентификатор пользователя, отправившего заявку.
     */
    @Schema(description = "Идентификатор пользователя, отправившего заявку", example = "1")
    private Long requester;

    /**
     * Дата и время создания заявки.
     */
    @Schema(description = "Дата и время создания заявки (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2024-07-30 20:30:00")
    private LocalDateTime created;

    /**
     * Статус заявки.
     */
    @Schema(description = "Статус заявки", example = "PENDING")
    private String status;
}

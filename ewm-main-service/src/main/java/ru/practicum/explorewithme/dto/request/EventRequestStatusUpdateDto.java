package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.enums.RequestStatus;

import java.util.List;

/**
 * Данные для изменения статуса запроса на участие в событии.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Данные для изменения статуса запроса на участие в событии")
public class EventRequestStatusUpdateDto {
    /**
     * Идентификаторы запросов на участие в событии текущего пользователя.
     */
    @NotNull(message = "Список идентификаторов не может быть пустым")
    @Schema(description = "Идентификаторы запросов на участие в событии текущего пользователя")
    private List<Long> requestIds;

    /**
     * Новый статус запроса на участие в событии текущего пользователя.
     */
    @NotNull(message = "Новый статус не может быть пустым")
    @Schema(description = "Новый статус запроса на участие в событии текущего пользователя")
    private RequestStatus status;

    /**
     * Проверка.
     * Статус должен быть CONFIRMED или REJECTED.
     */
    @AssertTrue(message = "Статус должен быть CONFIRMED или REJECTED")
    private boolean isStatusConfirmedOrRejected() {
        if (status != null) {
            return status.equals(RequestStatus.CONFIRMED) || status.equals(RequestStatus.REJECTED);
        }

        return true;
    }
}
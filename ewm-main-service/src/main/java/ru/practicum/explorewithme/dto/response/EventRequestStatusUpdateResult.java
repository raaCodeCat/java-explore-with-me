package ru.practicum.explorewithme.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Результат подтверждения/отклонения заявок на участие в событии")
public class EventRequestStatusUpdateResult {
    List<RequestView> confirmedRequests = List.of();

    List<RequestView> rejectedRequests = List.of();
}

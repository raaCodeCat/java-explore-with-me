package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Широта и долгота места проведения события.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Широта и долгота места проведения события")
public class LocationDto {
    /**
     * Широта.
     */
    @Schema(description = "Широта")
    private Float lat;

    /**
     * Долгота.
     */
    @Schema(description = "Долгота")
    private Float lon;
}

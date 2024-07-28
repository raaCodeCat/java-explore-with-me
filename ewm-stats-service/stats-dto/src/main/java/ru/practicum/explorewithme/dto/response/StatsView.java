package ru.practicum.explorewithme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto для возврата статистики посещений.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatsView {
    /**
     * Название сервиса.
     */
    private String app;

    /**
     * URI, посещения.
     */
    private String uri;

    /**
     * Количество поседений.
     */
    private Long hits;
}

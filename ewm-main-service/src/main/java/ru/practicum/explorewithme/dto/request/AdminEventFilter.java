package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Фильтр для получения списка событий для администратора.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Фильтр для получения списка событий для администратора")
public class AdminEventFilter {
    /**
     * Список идентификаторов пользователей, чьи события нужно найти.
     */
    private List<Long> users;

    /**
     * Список состояний, в которых находятся искомые события.
     */
    private List<EventState> states;

    /**
     * Список идентификаторов категорий в которых будет вестись поиск.
     */
    private List<Long> categories;

    /**
     * Дата и время не раньше которых должно произойти событие.
     */

    private LocalDateTime rangeStart;

    /**
     * Дата и время не позже которых должно произойти событие.
     */
    private LocalDateTime rangeEnd;

    /**
     * Количество событий, которые нужно пропустить для формирования текущего набора.
     */
    private Integer from;

    /**
     * Количество событий в наборе.
     */
    private Integer size;
}

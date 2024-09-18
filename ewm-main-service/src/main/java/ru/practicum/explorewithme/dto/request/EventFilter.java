package ru.practicum.explorewithme.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.enums.EventSort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Фильтр для получения списка событий.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Фильтр для получения списка событий")
public class EventFilter {
    /**
     * Текст для поиска в содержимом аннотации и подробном описании события.
     */
    private String text;

    /**
     * Список идентификаторов категорий в которых будет вестись поиск.
     */
    private List<Long> categories;

    /**
     * Поиск только платных/бесплатных событий.
     */
    private Boolean paid;

    /**
     * Дата и время не раньше которых должно произойти событие.
     */

    private LocalDateTime rangeStart;

    /**
     * Дата и время не позже которых должно произойти событие.
     */
    private LocalDateTime rangeEnd;

    /**
     * Только события у которых не исчерпан лимит запросов на участие.
     */
    private Boolean onlyAvailable;

    /**
     * Вариант сортировки: по дате события или по количеству просмотров.
     */
    private EventSort sort;

    /**
     * Количество событий, которые нужно пропустить для формирования текущего набора.
     */
    private Integer from;

    /**
     * Количество событий в наборе.
     */
    private Integer size;
}

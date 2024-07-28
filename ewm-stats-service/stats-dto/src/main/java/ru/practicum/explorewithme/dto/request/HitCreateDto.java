package ru.practicum.explorewithme.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Dto для добавления посещения.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HitCreateDto {
    /**
     * Название сервиса, для которого собирается статистика о посещениях.
     */
    private String app;

    /**
     * URI, по которому был выполнено посещение.
     */
    private String uri;

    /**
     * IP пользователя, выполнившего посещение.
     */
    private String ip;

    /**
     * Дата и время посещение.
     */
    private String timestamp;
}

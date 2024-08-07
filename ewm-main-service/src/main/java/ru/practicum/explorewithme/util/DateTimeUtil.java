package ru.practicum.explorewithme.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    public static final String PATTERN_FOR_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_FOR_ERROR = "YYYY-MM-DD HH:MM:SS";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FOR_FORMATTER);
}

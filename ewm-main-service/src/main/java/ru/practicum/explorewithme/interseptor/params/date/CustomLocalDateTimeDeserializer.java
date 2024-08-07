package ru.practicum.explorewithme.interseptor.params.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.practicum.explorewithme.exception.DateTimeDeserializerException;
import ru.practicum.explorewithme.util.DateTimeUtil;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public CustomLocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
    ) throws IOException {
        try {
            return LocalDateTime.parse(jsonParser.getText(), DateTimeUtil.formatter);
        } catch (DateTimeException e) {
            final String paramName = jsonParser.getParsingContext().getCurrentName();
            final String errorMessage = String.format(
                    "%s: Дата и время должны быть указанны быть в формате %s",
                    paramName,
                    DateTimeUtil.PATTERN_FOR_ERROR
            );

            throw new DateTimeDeserializerException(errorMessage);
        }
    }
}

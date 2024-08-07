package ru.practicum.explorewithme.interseptor.params.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.practicum.explorewithme.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    public CustomLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(
            LocalDateTime localDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(localDateTime.format(DateTimeUtil.formatter));
    }
}

package ru.practicum.explorewithme.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.explorewithme.interseptor.params.date.CustomLocalDateTimeDeserializer;
import ru.practicum.explorewithme.interseptor.params.date.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonDateCustomizer() {
        return builder -> builder
                .deserializerByType(LocalDateTime.class, new CustomLocalDateTimeDeserializer())
                .serializerByType(LocalDateTime.class, new CustomLocalDateTimeSerializer());
    }
}

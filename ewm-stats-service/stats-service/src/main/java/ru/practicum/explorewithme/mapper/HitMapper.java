package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.dto.request.HitCreateDto;
import ru.practicum.explorewithme.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {
    @Mapping(source = "timestamp", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Hit convert(HitCreateDto dto);
}

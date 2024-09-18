package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.dto.request.EventCreateDto;
import ru.practicum.explorewithme.dto.response.EventForCommentView;
import ru.practicum.explorewithme.dto.response.EventShortView;
import ru.practicum.explorewithme.dto.response.EventView;
import ru.practicum.explorewithme.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "category.id", source = "category")
    Event convert(EventCreateDto eventDto);

    @Mapping(target = "location.lat", source = "event.lat")
    @Mapping(target = "location.lon", source = "event.lon")
    @Mapping(target = "views", source = "viewCount", defaultValue = "0L")
    EventView convert(Event event, Long viewCount);

    @Mapping(target = "location.lat", source = "event.lat")
    @Mapping(target = "location.lon", source = "event.lon")
    List<EventView> convert(List<Event> events);

    EventShortView convertShort(Event event);

    List<EventShortView> convertShort(List<Event> events);

    @Mapping(target = "id", source = "eventId")
    Event convert(Long eventId);

    EventForCommentView convertForComment(Event event);
}

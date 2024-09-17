package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "event.id", source = "event")
    @Mapping(target = "author.id", source = "author")
    Comment convert(CommentCreateDto createDto);

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    CommentShortView convert(Comment comment);

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    List<CommentShortView> convert(List<Comment> comments);
}

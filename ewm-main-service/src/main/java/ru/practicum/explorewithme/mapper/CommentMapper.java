package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.dto.request.CommentCreateDto;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.dto.response.CommentView;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "author.id", source = "author")
    Comment convert(CommentCreateDto createDto);

    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    CommentShortView convertShort(Comment comment);

    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    List<CommentShortView> convertShort(List<Comment> comments);

    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    CommentView convert(Comment comments);

    @Mapping(target = "reactions.likeCount", source = "likeCount")
    @Mapping(target = "reactions.dislikeCount", source = "dislikeCount")
    List<CommentView> convert(List<Comment> comments);
}

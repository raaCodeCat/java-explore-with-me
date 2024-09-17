package ru.practicum.explorewithme.dto.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.response.CommentShortView;
import ru.practicum.explorewithme.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    CommentShortView createComment(@RequestBody CommentCreateDto createDto) {
        return commentService.createComment(createDto);
    }

    @GetMapping("/{commentId}")
    CommentShortView getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }
}

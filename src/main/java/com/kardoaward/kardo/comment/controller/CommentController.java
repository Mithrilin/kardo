package com.kardoaward.kardo.comment.controller;

import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.service.CommentService;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentService commentService;

    private final CommentValidationHelper commentValidationHelper;

    @PostMapping("/videos/{videoId}")
    public CommentDto createComment(@RequestHeader("X-Requestor-Id") Long requestorId,
                                    @PathVariable @Positive Long videoId,
                                    @RequestBody @Valid NewCommentRequest newCommentRequest) {
        log.info("Добавление пользователем с ИД {} нового комментария к видео-клипу с ИД {}.", requestorId, videoId);
        commentValidationHelper.isUserAuthor(requestorId, newCommentRequest.getAuthorId());
        return commentService.addComment(requestorId, videoId, newCommentRequest);
    }
}

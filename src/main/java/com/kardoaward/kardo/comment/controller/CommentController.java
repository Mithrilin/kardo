package com.kardoaward.kardo.comment.controller;

import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.model.dto.UpdateCommentRequest;
import com.kardoaward.kardo.comment.service.CommentService;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.user.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentService commentService;

    private final CommentValidationHelper commentValidationHelper;

    @PostMapping("/videos/{videoId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto createComment(@PathVariable @Positive Long videoId,
                                    @RequestBody @Valid NewCommentRequest newCommentRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление комментария к видео-клипу с ИД {}.", videoId);
        commentValidationHelper.isUserAuthor(requestor, newCommentRequest.getAuthorId());
        return commentService.addComment(requestor, videoId, newCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public void deleteCommentById(@PathVariable @Positive Long commentId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление комментария с ИД {}.", commentId);
        commentService.deleteCommentById(requestor, commentId);
    }

    @GetMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto getCommentById(@PathVariable @Positive Long commentId) {
        log.info("Возвращение комментария с ИД {}.", commentId);
        return commentService.getCommentById(commentId);
    }

    @PatchMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto updateCommentById(@PathVariable @Positive Long commentId,
                                        @RequestBody @Valid UpdateCommentRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своего комментария с ИД {}.", requestor.getId(), commentId);
        return commentService.updateCommentById(requestor, commentId, request);
    }

    @GetMapping("/videos/{videoId}")
    @Secured({"ADMIN", "USER"})
    public List<CommentDto> getCommentsByVideoId(@PathVariable @Positive Long videoId,
                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getCommentsByVideoId(videoId, from, size);
    }
}

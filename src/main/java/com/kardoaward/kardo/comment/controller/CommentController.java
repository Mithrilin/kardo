package com.kardoaward.kardo.comment.controller;

import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.model.dto.UpdateCommentRequest;
import com.kardoaward.kardo.comment.service.CommentService;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import com.kardoaward.kardo.security.MyUserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public CommentDto createComment(@PathVariable @Positive Long videoId,
                                    @RequestBody @Valid NewCommentRequest newCommentRequest) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Добавление пользователем с ИД {} нового комментария к видео-клипу с ИД {}.", requestorId, videoId);
        commentValidationHelper.isUserAuthor(requestorId, newCommentRequest.getAuthorId());
        return commentService.addComment(requestorId, videoId, newCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteCommentById(@PathVariable @Positive Long commentId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Удаление пользователем с ИД {} своего комментария с ИД {}.", requestorId, commentId);
        commentService.deleteCommentById(requestorId, commentId);
    }

    @GetMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public CommentDto getCommentById(@PathVariable @Positive Long commentId) {
        log.info("Возвращение комментария с ИД {}.", commentId);
        return commentService.getCommentById(commentId);
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public CommentDto updateCommentById(@PathVariable @Positive Long commentId,
                                        @RequestBody @Valid UpdateCommentRequest request) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Обновление пользователем с ИД {} своего комментария с ИД {}.", requestorId, commentId);
        return commentService.updateCommentById(requestorId, commentId, request);
    }

    @GetMapping("/videos/{videoId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<CommentDto> getCommentsByVideoId(@PathVariable @Positive Long videoId,
                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getCommentsByVideoId(videoId, from, size);
    }
}

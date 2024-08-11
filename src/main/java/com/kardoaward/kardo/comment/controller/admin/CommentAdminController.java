package com.kardoaward.kardo.comment.controller.admin;

import com.kardoaward.kardo.comment.service.CommentService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/comments")
@Validated
public class CommentAdminController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCommentByIdByAdmin(@PathVariable @Positive Long commentId) {
        log.info("Удаление администратором комментария с ИД {}.", commentId);
        commentService.deleteCommentByIdByAdmin(commentId);
    }
}

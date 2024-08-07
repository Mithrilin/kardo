package com.kardoaward.kardo.comment.service.helper;

import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.repository.CommentRepository;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CommentValidationHelper {

    private final CommentRepository commentRepository;

    public void isUserAuthor(Long requestorId, Long authorId) {
        if (!requestorId.equals(authorId)) {
            log.error("Пользователь с ИД {} не является создателем комментария.", requestorId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем комментария.",
                    requestorId));
        }
    }

    public Comment isCommentPresent(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isEmpty()) {
            log.error("Комментарий с ИД {} отсутствует в БД.", commentId);
            throw new NotFoundException(String.format("Комментарий с ИД %d отсутствует в БД.", commentId));
        }

        return optionalComment.get();
    }
}

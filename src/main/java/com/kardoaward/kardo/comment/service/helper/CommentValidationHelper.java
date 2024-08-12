package com.kardoaward.kardo.comment.service.helper;

import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.repository.CommentRepository;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.exception.NotValidException;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CommentValidationHelper {

    private final CommentRepository commentRepository;

    public void isUserAuthor(User requestor, Long authorId) {
        if (!authorId.equals(requestor.getId()) && requestor.getRole() != Role.ADMIN ) {
            log.error("Пользователь с ИД {} не является создателем комментария или администратором.", requestor.getId());
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем комментария " +
                            "или администратором.", requestor.getId()));
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

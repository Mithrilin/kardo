package com.kardoaward.kardo.comment.service.helper;

import com.kardoaward.kardo.exception.NotValidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommentValidationHelper {

    public void isUserAuthor(Long requestorId, Long authorId) {
        if (!requestorId.equals(authorId)) {
            log.error("Пользователь с ИД {} не является создателем комментария.", requestorId);
            throw new NotValidException(String.format("Пользователь с ИД %d не является создателем комментария.",
                    requestorId));
        }
    }
}

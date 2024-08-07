package com.kardoaward.kardo.comment.service;

import com.kardoaward.kardo.comment.mapper.CommentMapper;
import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.repository.CommentRepository;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserValidationHelper userValidationHelper;
    private final VideoClipValidationHelper videoClipValidationHelper;
    private final CommentValidationHelper commentValidationHelper;

    @Override
    @Transactional
    public CommentDto addComment(Long requestorId, Long videoId, NewCommentRequest newCommentRequest) {
        User user = userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        Comment comment = commentMapper.newCommentRequestToComment(newCommentRequest, user, videoClip);
        Comment returnedComment = commentRepository.save(comment);
        CommentDto commentDto = commentMapper.commentToCommentDto(returnedComment);
        log.info("Комментарий с ИД {} пользователя с ИД {} к видео-клипу с ИД {} создан.", commentDto.getId(),
                requestorId, videoId);
        return commentDto;
    }

    @Override
    @Transactional
    public void deleteCommentById(Long requestorId, Long commentId) {
        userValidationHelper.isUserPresent(requestorId);
        Comment comment = commentValidationHelper.isCommentPresent(commentId);
        commentValidationHelper.isUserAuthor(requestorId, comment.getAuthor().getId());
        commentRepository.deleteById(commentId);
        log.info("Комментарий с ИД {} пользователя с ИД {} удалён.", commentId, requestorId);
    }
}

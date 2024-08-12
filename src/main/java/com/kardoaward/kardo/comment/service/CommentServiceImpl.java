package com.kardoaward.kardo.comment.service;

import com.kardoaward.kardo.comment.mapper.CommentMapper;
import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.model.dto.UpdateCommentRequest;
import com.kardoaward.kardo.comment.repository.CommentRepository;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public CommentDto addComment(User requestor, Long videoId, NewCommentRequest newCommentRequest) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        Comment comment = commentMapper.newCommentRequestToComment(newCommentRequest, requestor, videoClip);
        Comment returnedComment = commentRepository.save(comment);
        CommentDto commentDto = commentMapper.commentToCommentDto(returnedComment);
        log.info("Комментарий с ИД {} пользователя с ИД {} к видео-клипу с ИД {} создан.", commentDto.getId(),
                requestor.getId(), videoId);
        return commentDto;
    }

    @Override
    @Transactional
    public void deleteCommentById(User requestor, Long commentId) {
        Comment comment = commentValidationHelper.isCommentPresent(commentId);
        commentValidationHelper.isUserAuthor(requestor, comment.getAuthor().getId());
        commentRepository.deleteById(commentId);
        log.info("Комментарий с ИД {} пользователя с ИД {} удалён.", commentId, requestor.getId());
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentValidationHelper.isCommentPresent(commentId);
        CommentDto commentDto = commentMapper.commentToCommentDto(comment);
        log.info("Возвращение комментария с ИД {}.", commentId);
        return commentDto;
    }

    @Override
    @Transactional
    public CommentDto updateCommentById(User requestor, Long commentId, UpdateCommentRequest request) {
        Comment comment = commentValidationHelper.isCommentPresent(commentId);
        commentValidationHelper.isUserAuthor(requestor, comment.getAuthor().getId());
        commentMapper.updateComment(request, comment);
        Comment updatedComment = commentRepository.save(comment);
        CommentDto commentDto = commentMapper.commentToCommentDto(updatedComment);
        log.info("Комментарий с ID {} обновлён.", commentId);
        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsByVideoId(Long videoId, int from, int size) {
        videoClipValidationHelper.isVideoClipPresent(videoId);
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Comment> commentPage = commentRepository.findByVideoClip_Id(videoId, pageRequest);

        if (commentPage.isEmpty()) {
            log.info("Не нашлось комментариев по заданным параметрам.");
            return new ArrayList<>();
        }

        List<Comment> comments = commentPage.getContent();
        List<CommentDto> commentDtos = commentMapper.commentListToCommentDtoList(comments);
        log.info("Список комментариев с номера {} размером {} возвращён.", from, commentDtos.size());
        return commentDtos;
    }
}

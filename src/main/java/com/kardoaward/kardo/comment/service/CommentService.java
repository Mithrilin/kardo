package com.kardoaward.kardo.comment.service;

import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.model.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    CommentDto addComment(Long requestorId, Long videoId, NewCommentRequest newCommentRequest);

    void deleteCommentById(Long requestorId, Long commentId);

    void deleteCommentByIdByAdmin(Long commentId);

    CommentDto getCommentById(Long commentId);

    CommentDto updateCommentById(Long requestorId, Long commentId, UpdateCommentRequest request);

    List<CommentDto> getCommentsByVideoId(Long videoId, int from, int size);
}

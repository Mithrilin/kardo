package com.kardoaward.kardo.comment.service;

import com.kardoaward.kardo.comment.dto.CommentDto;
import com.kardoaward.kardo.comment.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.dto.UpdateCommentRequest;
import com.kardoaward.kardo.user.model.User;

import java.util.List;

public interface CommentService {

    CommentDto addComment(User requestor, Long videoId, NewCommentRequest newCommentRequest);

    void deleteCommentById(User requestor, Long commentId);

    CommentDto getCommentById(Long commentId);

    CommentDto updateCommentById(User requestor, Long commentId, UpdateCommentRequest request);

    List<CommentDto> getCommentsByVideoId(Long videoId, int from, int size);
}

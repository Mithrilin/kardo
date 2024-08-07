package com.kardoaward.kardo.comment.service;

import com.kardoaward.kardo.comment.model.dto.CommentDto;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;

public interface CommentService {

    CommentDto addComment(Long requestorId, Long videoId, NewCommentRequest newCommentRequest);
}

package com.kardoaward.kardo.comment.mapper;

import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.model.dto.NewCommentRequest;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "author")
    @Mapping(source = "videoClip", target = "videoClip")
    Comment newCommentRequestToComment(NewCommentRequest newCommentRequest, User user, VideoClip videoClip);
}

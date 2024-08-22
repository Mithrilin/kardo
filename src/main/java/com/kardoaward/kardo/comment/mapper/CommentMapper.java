package com.kardoaward.kardo.comment.mapper;

import com.kardoaward.kardo.comment.model.Comment;
import com.kardoaward.kardo.comment.dto.CommentDto;
import com.kardoaward.kardo.comment.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.dto.UpdateCommentRequest;
import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.mapper.VideoClipMapper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, VideoClipMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "author")
    @Mapping(source = "videoClip", target = "videoClip")
    Comment newCommentRequestToComment(NewCommentRequest newCommentRequest, User user, VideoClip videoClip);

    @Mapping(source = "comment.author", target = "authorDto")
    @Mapping(source = "comment.videoClip", target = "videoClipDto")
    CommentDto commentToCommentDto(Comment comment);

    void updateComment(UpdateCommentRequest request, @MappingTarget Comment comment);

    List<CommentDto> commentListToCommentDtoList(List<Comment> comments);
}

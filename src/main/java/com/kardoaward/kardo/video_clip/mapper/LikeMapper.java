package com.kardoaward.kardo.video_clip.mapper;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.like.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "creator")
    @Mapping(source = "videoClip", target = "videoClip")
    Like toLike(User user, VideoClip videoClip);
}

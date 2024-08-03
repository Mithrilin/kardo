package com.kardoaward.kardo.video_clip.mapper;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VideoClipMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "creator")
    VideoClip newVideoClipRequestToVideoClip(NewVideoClipRequest request, User user);
}

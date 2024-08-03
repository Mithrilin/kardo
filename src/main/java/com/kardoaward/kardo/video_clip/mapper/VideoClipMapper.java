package com.kardoaward.kardo.video_clip.mapper;

import com.kardoaward.kardo.user.mapper.UserMapper;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface VideoClipMapper {

    @Mapping(target = "id", constant = "0L")
    @Mapping(source = "user", target = "creator")
    VideoClip newVideoClipRequestToVideoClip(NewVideoClipRequest request, User user);

    @Mapping(source = "returnedVideoClip.creator", target = "creatorDto")
    VideoClipDto videoClipToVideoClipDto(VideoClip returnedVideoClip);

    void updateVideoClip(UpdateVideoClipRequest request, @MappingTarget VideoClip videoClip);
}

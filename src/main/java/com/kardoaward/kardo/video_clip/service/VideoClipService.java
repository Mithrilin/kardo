package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;

public interface VideoClipService {

    VideoClipDto addVideoClip(Long requestorId, NewVideoClipRequest request);

    void deleteVideoClipById(Long requestorId, Long videoId);

    void deleteVideoClipByIdByAdmin(Long videoId);

    VideoClipDto getVideoClipsById(Long videoId);
}

package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoClipService {

    VideoClipDto addVideoClip(Long requestorId, NewVideoClipRequest request, MultipartFile file);

    void deleteVideoClipById(Long requestorId, Long videoId);

    void deleteVideoClipByIdByAdmin(Long videoId);

    VideoClipDto getVideoClipById(Long videoId);

    VideoClipDto updateVideoClipById(Long requestorId, Long videoId, UpdateVideoClipRequest request);

    List<VideoClipDto> getVideoClipsByHashtag(String hashtag, int from, int size);

    VideoClipDto addLikeByVideoClipId(Long requestorId, Long videoId);

    VideoClipDto deleteLikeByVideoClipId(Long requestorId, Long videoId);
}

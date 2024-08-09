package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipShortDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoClipService {

    VideoClipShortDto addVideoClip(Long requestorId, NewVideoClipRequest request, MultipartFile file);

    void deleteVideoClipById(Long requestorId, Long videoId);

    void deleteVideoClipByIdByAdmin(Long videoId);

    VideoClipDto getVideoClipById(Long videoId);

    VideoClipShortDto updateVideoClipById(Long requestorId, Long videoId, UpdateVideoClipRequest request);

    List<VideoClipShortDto> getVideoClipsByHashtag(String hashtag, int from, int size);

    VideoClipShortDto addLikeByVideoClipId(Long requestorId, Long videoId);

    VideoClipShortDto deleteLikeByVideoClipId(Long requestorId, Long videoId);
}

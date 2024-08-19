package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.VideoClipDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoClipService {

    VideoClipDto addVideoClip(User requestor, NewVideoClipRequest request, MultipartFile file);

    void deleteVideoClipById(User requestor, Long videoId);

    VideoClipDto getVideoClipById(Long videoId);

    VideoClipDto updateVideoClipById(User requestor, Long videoId, UpdateVideoClipRequest request);

    List<VideoClipDto> getVideoClipsByHashtag(String hashtag, int from, int size);

    VideoClipDto addLikeByVideoClipId(User requestor, Long videoId);

    VideoClipDto deleteLikeByVideoClipId(Long requestorId, Long videoId);
}

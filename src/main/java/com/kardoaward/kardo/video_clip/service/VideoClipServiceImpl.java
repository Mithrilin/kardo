package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import com.kardoaward.kardo.video_clip.mapper.VideoClipMapper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class VideoClipServiceImpl implements VideoClipService {

    private final VideoClipRepository videoClipRepository;

    private final VideoClipMapper videoClipMapper;

    private final VideoClipValidationHelper videoClipValidationHelper;
    private final UserValidationHelper userValidationHelper;

    @Override
    @Transactional
    public VideoClipDto addVideoClip(Long requestorId, NewVideoClipRequest request) {
        User user = userValidationHelper.isUserPresent(requestorId);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, request.getCreatorId());
        VideoClip videoClip = videoClipMapper.newVideoClipRequestToVideoClip(request, user);
        VideoClip returnedVideoClip = videoClipRepository.save(videoClip);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(returnedVideoClip);
        log.info("Видео-клип с ID = {} создан.", videoClipDto.getId());
        return videoClipDto;
    }

    @Override
    @Transactional
    public void deleteVideoClipById(Long requestorId, Long videoId) {
        userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, videoClip.getCreator().getId());
        videoClipRepository.deleteById(videoId);
        log.info("Видео-клип с ID {} удалён.", videoId);
    }
}

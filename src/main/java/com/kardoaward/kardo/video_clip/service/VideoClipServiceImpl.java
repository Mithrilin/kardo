package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import com.kardoaward.kardo.video_clip.mapper.VideoClipMapper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        log.info("Видео-клип с ID {} удалён пользователем с ИД {}.", videoId, requestorId);
    }

    @Override
    @Transactional
    public void deleteVideoClipByIdByAdmin(Long videoId) {
        videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipRepository.deleteById(videoId);
        log.info("Видео-клип с ID {} удалён администратором.", videoId);
    }

    @Override
    public VideoClipDto getVideoClipsById(Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(videoClip);
        log.info("Видео-клип с ИД {} возвращен.", videoId);
        return videoClipDto;
    }

    @Override
    @Transactional
    public VideoClipDto updateVideoClipById(Long requestorId, Long videoId, UpdateVideoClipRequest request) {
        userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, videoClip.getCreator().getId());
        videoClipMapper.updateVideoClip(request, videoClip);
        VideoClip updatedVideoClip = videoClipRepository.save(videoClip);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(updatedVideoClip);
        log.info("Видео-клип с ID {} обновлён.", videoId);
        return videoClipDto;
    }

    @Override
    public List<VideoClipDto> getVideoClipsByHashtag(String hashtag, int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<VideoClip> videoClipsPage = videoClipRepository.findAllByHashtag(hashtag, pageRequest);

        if (videoClipsPage.isEmpty()) {
            log.info("Не нашлось видео-клипов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<VideoClip> videoClips = videoClipsPage.getContent();
        List<VideoClipDto> videoClipDtos = videoClipMapper.videoClipListToVideoClipDtoList(videoClips);
        log.info("Список видео-клипов с номера {} размером {} возвращён.", from, videoClipDtos.size());
        return videoClipDtos;
    }
}

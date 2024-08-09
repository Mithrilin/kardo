package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.user.service.helper.UserValidationHelper;
import com.kardoaward.kardo.video_clip.mapper.LikeMapper;
import com.kardoaward.kardo.video_clip.mapper.VideoClipMapper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipShortDto;
import com.kardoaward.kardo.video_clip.model.like.Like;
import com.kardoaward.kardo.video_clip.repository.LikeRepository;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VideoClipServiceImpl implements VideoClipService {

    private final VideoClipRepository videoClipRepository;
    private final LikeRepository likeRepository;

    private final VideoClipMapper videoClipMapper;
    private final LikeMapper likeMapper;

    private final VideoClipValidationHelper videoClipValidationHelper;
    private final UserValidationHelper userValidationHelper;

    @Override
    @Transactional
    public VideoClipShortDto addVideoClip(Long requestorId, NewVideoClipRequest request, MultipartFile file) {
        User user = userValidationHelper.isUserPresent(requestorId);
        String folderPath = "C:/Users/Roman/Desktop/test/users/";
        String path = folderPath + requestorId + "/videos/";
        File videoPath = new File(path);
        videoPath.mkdirs();
        String newVideoPath = path + file.getOriginalFilename();

        try {
            file.transferTo(new File(newVideoPath));
        } catch (IOException e) {
            throw new FileContentException("Не удалось сохранить файл: " + newVideoPath);
        }

        VideoClip videoClip = videoClipMapper.newVideoClipRequestToVideoClip(request, user, newVideoPath);
        VideoClip returnedVideoClip = videoClipRepository.save(videoClip);
        VideoClipShortDto videoClipShortDto = videoClipMapper.videoClipToVideoClipShortDto(returnedVideoClip);
        log.info("Видео-клип с ID = {} создан.", videoClipShortDto.getId());
        return videoClipShortDto;
    }

    @Override
    @Transactional
    public void deleteVideoClipById(Long requestorId, Long videoId) {
        userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, videoClip.getCreator().getId());
        deleteVideo(videoClip);
        log.info("Видео-клип с ID {} удалён пользователем с ИД {}.", videoId, requestorId);
    }

    @Override
    @Transactional
    public void deleteVideoClipByIdByAdmin(Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        deleteVideo(videoClip);
        log.info("Видео-клип с ID {} удалён администратором.", videoId);
    }

    @Override
    public VideoClipDto getVideoClipById(Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(videoClip);
        File videoFile = new File(videoClip.getVideoLink());

        try {
            videoClipDto.setVideoClip(Files.readAllBytes(videoFile.toPath()));
        } catch (IOException e) {
            throw new FileContentException("Не удалось обработать файл.");
        }

        log.info("Видео-клип с ИД {} возвращен.", videoId);
        return videoClipDto;
    }

    @Override
    @Transactional
    public VideoClipShortDto updateVideoClipById(Long requestorId, Long videoId, UpdateVideoClipRequest request) {
        userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, videoClip.getCreator().getId());
        videoClipMapper.updateVideoClip(request, videoClip);
        VideoClip updatedVideoClip = videoClipRepository.save(videoClip);
        VideoClipShortDto videoClipShortDto = videoClipMapper.videoClipToVideoClipShortDto(updatedVideoClip);
        log.info("Видео-клип с ID {} обновлён.", videoId);
        return videoClipShortDto;
    }

    @Override
    public List<VideoClipShortDto> getVideoClipsByHashtag(String hashtag, int from, int size) {
        int page = from / size;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<VideoClip> videoClipsPage = videoClipRepository.findAllByHashtag(hashtag, pageRequest);

        if (videoClipsPage.isEmpty()) {
            log.info("Не нашлось видео-клипов по заданным параметрам.");
            return new ArrayList<>();
        }

        List<VideoClip> videoClips = videoClipsPage.getContent();
        List<VideoClipShortDto> videoClipShortDtos = videoClipMapper.videoClipListToVideoClipShortDtoList(videoClips);
        log.info("Список видео-клипов с номера {} размером {} возвращён.", from, videoClipShortDtos.size());
        return videoClipShortDtos;
    }

    @Override
    @Transactional
    public VideoClipShortDto addLikeByVideoClipId(Long requestorId, Long videoId) {
        User user = userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorNotCreatorVideo(requestorId, videoClip.getCreator().getId());
        Like like = likeMapper.toLike(user, videoClip);
        likeRepository.save(like);
        Integer likesCount = videoClip.getLikesCount();
        videoClip.setLikesCount(++likesCount);
        VideoClipShortDto videoClipShortDto = videoClipMapper.videoClipToVideoClipShortDto(videoClip);
        log.info("Лайк пользователя с ID {} к видео-клипу с ИД {} добавлен.", requestorId, videoId);
        return videoClipShortDto;
    }

    @Override
    @Transactional
    public VideoClipShortDto deleteLikeByVideoClipId(Long requestorId, Long videoId) {
        userValidationHelper.isUserPresent(requestorId);
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        Like like = videoClipValidationHelper.isRequestorLikedVideoClip(requestorId, videoId);
        likeRepository.delete(like);
        Integer likesCount = videoClip.getLikesCount();
        videoClip.setLikesCount(--likesCount);
        VideoClipShortDto videoClipShortDto = videoClipMapper.videoClipToVideoClipShortDto(videoClip);
        log.info("Лайк пользователя с ID {} к видео-клипу с ИД {} удалён.", requestorId, videoId);
        return videoClipShortDto;
    }

    private void deleteVideo(VideoClip videoClip) {
        String path = videoClip.getVideoLink();

        try {
            FileUtils.forceDelete(new File(path));
        } catch (IOException e) {
            throw new FileContentException("Не удалось удалить файл: " + path);
        }

        videoClipRepository.delete(videoClip);
    }
}

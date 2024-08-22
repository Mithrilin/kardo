package com.kardoaward.kardo.video_clip.service;

import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.mapper.LikeMapper;
import com.kardoaward.kardo.video_clip.mapper.VideoClipMapper;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.model.like.Like;
import com.kardoaward.kardo.video_clip.repository.LikeRepository;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class VideoClipServiceImpl implements VideoClipService {

    private final VideoClipRepository videoClipRepository;
    private final LikeRepository likeRepository;

    private final VideoClipMapper videoClipMapper;
    private final LikeMapper likeMapper;

    private final VideoClipValidationHelper videoClipValidationHelper;

    private final String FOLDER_PATH;

    public VideoClipServiceImpl(VideoClipRepository videoClipRepository,
                                LikeRepository likeRepository,
                                VideoClipMapper videoClipMapper,
                                LikeMapper likeMapper,
                                VideoClipValidationHelper videoClipValidationHelper,
                                @Value("${folder.path}") String FOLDER_PATH) {
        this.videoClipRepository = videoClipRepository;
        this.likeRepository = likeRepository;
        this.videoClipMapper = videoClipMapper;
        this.likeMapper = likeMapper;
        this.videoClipValidationHelper = videoClipValidationHelper;
        this.FOLDER_PATH = FOLDER_PATH;
    }

    @Override
    @Transactional
    public VideoClipDto addVideoClip(User requestor, NewVideoClipRequest request, MultipartFile file) {
        String path = FOLDER_PATH + "/users/" + requestor.getId() + "/videos/";
        File videoPath = new File(path);
        videoPath.mkdirs();
        String newVideoPath = path + file.getOriginalFilename();

        try {
            file.transferTo(new File(newVideoPath));
        } catch (IOException e) {
            throw new FileContentException("Не удалось сохранить файл: " + newVideoPath);
        }

        VideoClip videoClip = videoClipMapper.newVideoClipRequestToVideoClip(request, requestor, newVideoPath);
        VideoClip returnedVideoClip = videoClipRepository.save(videoClip);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(returnedVideoClip);
        log.info("Видео-клип с ID = {} создан.", videoClipDto.getId());
        return videoClipDto;
    }

    @Override
    @Transactional
    public void deleteVideoClipById(User requestor, Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideoOrAdmin(requestor, videoClip.getCreator().getId());
        deleteVideo(videoClip);
        log.info("Видео-клип с ID {} удалён.", videoId);
    }

    @Override
    public VideoClipDto getVideoClipById(Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(videoClip);
        log.info("Видео-клип с ИД {} возвращен.", videoId);
        return videoClipDto;
    }

    @Override
    @Transactional
    public VideoClipDto updateVideoClipById(User requestor, Long videoId, UpdateVideoClipRequest request) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorCreatorVideoOrAdmin(requestor, videoClip.getCreator().getId());
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

    @Override
    @Transactional
    public VideoClipDto addLikeByVideoClipId(User requestor, Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        videoClipValidationHelper.isRequestorNotCreatorVideo(requestor.getId(), videoClip.getCreator().getId());
        Like like = likeMapper.toLike(requestor, videoClip);
        likeRepository.save(like);
        Integer likesCount = videoClip.getLikesCount();
        videoClip.setLikesCount(++likesCount);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(videoClip);
        log.info("Лайк пользователя с ID {} к видео-клипу с ИД {} добавлен.", requestor.getId(), videoId);
        return videoClipDto;
    }

    @Override
    @Transactional
    public VideoClipDto deleteLikeByVideoClipId(Long requestorId, Long videoId) {
        VideoClip videoClip = videoClipValidationHelper.isVideoClipPresent(videoId);
        Like like = videoClipValidationHelper.isRequestorLikedVideoClip(requestorId, videoId);
        likeRepository.delete(like);
        Integer likesCount = videoClip.getLikesCount();
        videoClip.setLikesCount(--likesCount);
        VideoClipDto videoClipDto = videoClipMapper.videoClipToVideoClipDto(videoClip);
        log.info("Лайк пользователя с ID {} к видео-клипу с ИД {} удалён.", requestorId, videoId);
        return videoClipDto;
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

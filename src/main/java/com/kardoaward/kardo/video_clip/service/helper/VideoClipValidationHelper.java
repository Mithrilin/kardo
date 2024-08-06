package com.kardoaward.kardo.video_clip.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.model.like.Like;
import com.kardoaward.kardo.video_clip.repository.LikeRepository;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class VideoClipValidationHelper {

    private final VideoClipRepository videoClipRepository;
    private final LikeRepository likeRepository;

    public VideoClip isVideoClipPresent(Long videoId) {
        Optional<VideoClip> optionalVideoClip = videoClipRepository.findById(videoId);

        if (optionalVideoClip.isEmpty()) {
            log.error("Видео-клип с ИД {} отсутствует в БД.", videoId);
            throw new NotFoundException(String.format("Видео-клип с ИД %d отсутствует в БД.", videoId));
        }

        return optionalVideoClip.get();
    }

    public void isRequestorCreatorVideo(Long requestorId, Long creatorId) {
        if (!requestorId.equals(creatorId)) {
            log.error("Пользователь с ИД {} не является создателем видео.", requestorId);
            throw new BadRequestException(String.format("Пользователь с ИД %d не является создателем видео.",
                    requestorId));
        }
    }

    public void isRequestorNotCreatorVideo(Long requestorId, Long creatorId) {
        if (requestorId.equals(creatorId)) {
            log.error("Пользователь с ИД {} является создателем видео.", requestorId);
            throw new BadRequestException(String.format("Пользователь с ИД %d является создателем видео.",
                    requestorId));
        }
    }

    public Like isRequestorLikedVideoClip(Long requestorId, Long videoId) {
        Optional<Like> optionalLike = likeRepository.findByCreator_IdAndVideoClip_Id(requestorId, videoId);

        if (optionalLike.isEmpty()) {
            log.error("Пользователь с ИД {} не добавлял лайк к видео-клипу с ИД {}.", requestorId, videoId);
            throw new NotFoundException(String.format("Пользователь с ИД %d не добавлял лайк к видео-клипу с ИД %d.",
                    requestorId, videoId));
        }

        return optionalLike.get();
    }
}

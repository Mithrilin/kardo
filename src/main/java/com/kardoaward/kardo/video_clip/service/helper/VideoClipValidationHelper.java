package com.kardoaward.kardo.video_clip.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.exception.NotFoundException;
import com.kardoaward.kardo.video_clip.model.VideoClip;
import com.kardoaward.kardo.video_clip.repository.VideoClipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class VideoClipValidationHelper {

    private final VideoClipRepository videoClipRepository;

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
}

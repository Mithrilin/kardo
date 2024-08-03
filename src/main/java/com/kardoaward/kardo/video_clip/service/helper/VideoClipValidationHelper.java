package com.kardoaward.kardo.video_clip.service.helper;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class VideoClipValidationHelper {

    public void isRequestorCreatorVideo(Long requestorId, NewVideoClipRequest request) {
        if (!requestorId.equals(request.getCreatorId())) {
            log.error("Пользователь с ИД {} не является создателем видео.", requestorId);
            throw new BadRequestException(String.format("Пользователь с ИД %d не является создателем видео.",
                    requestorId));
        }
    }
}

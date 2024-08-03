package com.kardoaward.kardo.video_clip.controller;

import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.service.VideoClipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/videos")
@Validated
public class VideoClipController {

    private final VideoClipService videoClipService;

    @PostMapping
    public VideoClipDto createVideoClip(@RequestHeader("X-Requestor-Id") Long requestorId,
                                        @RequestBody @Valid NewVideoClipRequest request) {
        log.info("Добавление пользователем с ИД {} нового видео-клипа.", requestorId);
        return videoClipService.addVideoClip(requestorId, request);
    }

    @DeleteMapping("/{videoId}")
    public void deleteVideoClipById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                    @PathVariable @Positive Long videoId) {
        log.info("Удаление пользователем с ИД {} своего видео-клипа с ИД {}.", requestorId, videoId);
        videoClipService.deleteVideoClipById(requestorId, videoId);
    }
}

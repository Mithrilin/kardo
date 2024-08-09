package com.kardoaward.kardo.video_clip.controller;

import com.google.gson.Gson;
import com.kardoaward.kardo.video_clip.model.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.model.dto.VideoClipShortDto;
import com.kardoaward.kardo.video_clip.service.VideoClipService;
import com.kardoaward.kardo.video_clip.service.helper.VideoClipValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/videos")
@Validated
public class VideoClipController {

    private final VideoClipService videoClipService;

    private final VideoClipValidationHelper videoClipValidationHelper;

    @PostMapping
    public VideoClipShortDto createVideoClip(@RequestHeader("X-Requestor-Id") Long requestorId,
                                             @RequestParam("text") String json,
                                             @RequestParam("video") MultipartFile file) {
        log.info("Добавление пользователем с ИД {} нового видео-клипа.", requestorId);
        NewVideoClipRequest request = new Gson().fromJson(json, NewVideoClipRequest.class);
        videoClipValidationHelper.isRequestorCreatorVideo(requestorId, request.getCreatorId());
        return videoClipService.addVideoClip(requestorId, request, file);
    }

    @DeleteMapping("/{videoId}")
    public void deleteVideoClipById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                    @PathVariable @Positive Long videoId) {
        log.info("Удаление пользователем с ИД {} своего видео-клипа с ИД {}.", requestorId, videoId);
        videoClipService.deleteVideoClipById(requestorId, videoId);
    }

    @GetMapping("/{videoId}")
    public VideoClipDto getVideoClipById(@PathVariable @Positive Long videoId) {
        log.info("Возвращение видео-клипа с ИД {}.", videoId);
        return videoClipService.getVideoClipById(videoId);
    }

    @PatchMapping("/{videoId}")
    public VideoClipShortDto updateVideoClipById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                 @PathVariable @Positive Long videoId,
                                                 @RequestBody @Valid UpdateVideoClipRequest request) {
        log.info("Обновление пользователем с ИД {} видео-клипа с ИД {}.", requestorId, videoId);
        return videoClipService.updateVideoClipById(requestorId, videoId, request);
    }

    @GetMapping("/search")
    public List<VideoClipShortDto> getVideoClipsByHashtag(@RequestParam @Size(min = 2, max = 20) String hashtag,
                                                          @RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-клипов с хештегом {}.", hashtag);
        return videoClipService.getVideoClipsByHashtag(hashtag, from, size);
    }

    @PostMapping("/{videoId}/likes")
    public VideoClipShortDto addLikeByVideoClipId(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                  @PathVariable @Positive Long videoId) {
        log.info("Добавление пользователем с ИД {} лайка к видео-клипу с ИД {}.", requestorId, videoId);
        return videoClipService.addLikeByVideoClipId(requestorId, videoId);
    }

    @DeleteMapping("/{videoId}/likes")
    public VideoClipShortDto deleteLikeByVideoClipId(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                     @PathVariable @Positive Long videoId) {
        log.info("Удаление пользователем с ИД {} своего лайка к видео-клипу с ИД {}.", requestorId, videoId);
        return videoClipService.deleteLikeByVideoClipId(requestorId, videoId);
    }
}

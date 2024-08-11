package com.kardoaward.kardo.video_clip.controller.admin;

import com.kardoaward.kardo.video_clip.service.VideoClipService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/videos")
@Validated
public class VideoClipAdminController {

    private final VideoClipService videoClipService;

    @DeleteMapping("/{videoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteVideoClipByIdByAdmin(@PathVariable @Positive Long videoId) {
        log.info("Удаление администратором видео-клипа с ИД {}.", videoId);
        videoClipService.deleteVideoClipByIdByAdmin(videoId);
    }
}

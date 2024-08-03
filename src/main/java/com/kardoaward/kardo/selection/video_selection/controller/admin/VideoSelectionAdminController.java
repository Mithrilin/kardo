package com.kardoaward.kardo.selection.video_selection.controller.admin;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.selection.model.dto.UpdateSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/selections/video")
@Validated
public class VideoSelectionAdminController {

    private final VideoSelectionService videoSelectionService;

    @PostMapping
    public VideoSelectionDto createVideoSelection(@RequestBody @Valid
                                                  NewVideoSelectionRequest newVideoSelectionRequest) {

        if (newVideoSelectionRequest.getSelectionEnd().isBefore(newVideoSelectionRequest.getSelectionStart())) {
            log.error("Дата начала видео-отбора не может быть после его конца.");
            throw new BadRequestException("Дата начала видео-отбора не может быть после его конца.");
        }

        log.info("Добавление администратором нового видео-отбора {}.", newVideoSelectionRequest);
        return videoSelectionService.addVideoSelection(newVideoSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    public void deleteVideoSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором видео-отбора с ИД {}.", selectionId);
        videoSelectionService.deleteVideoSelection(selectionId);
    }

    @PatchMapping("/{selectionId}")
    public VideoSelectionDto updateVideoSelectionById(@PathVariable @Positive Long selectionId,
                                                      @RequestBody @Valid UpdateSelectionRequest request) {
        log.info("Обновление администратором видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.updateVideoSelectionById(selectionId, request);
    }
}

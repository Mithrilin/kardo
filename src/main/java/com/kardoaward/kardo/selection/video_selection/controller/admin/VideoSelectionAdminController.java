package com.kardoaward.kardo.selection.video_selection.controller.admin;

import com.kardoaward.kardo.selection.video_selection.model.UpdateVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
import com.kardoaward.kardo.selection.video_selection.service.helper.VideoSelectionValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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

    private final VideoSelectionValidationHelper videoSelectionValidationHelper;

    @PostMapping
    @Secured("ADMIN")
    public VideoSelectionDto createVideoSelection(@RequestBody @Valid
                                                  NewVideoSelectionRequest newVideoSelectionRequest) {
        log.info("Добавление администратором нового видео-отбора {}.", newVideoSelectionRequest);
        videoSelectionValidationHelper.isNewVideoSelectionDateValid(newVideoSelectionRequest);
        return videoSelectionService.addVideoSelection(newVideoSelectionRequest);
    }

    @DeleteMapping("/{selectionId}")
    @Secured("ADMIN")
    public void deleteVideoSelection(@PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором видео-отбора с ИД {}.", selectionId);
        videoSelectionService.deleteVideoSelection(selectionId);
    }

    @PatchMapping("/{selectionId}")
    @Secured("ADMIN")
    public VideoSelectionDto updateVideoSelectionById(@PathVariable @Positive Long selectionId,
                                                      @RequestBody @Valid UpdateVideoSelectionRequest request) {
        log.info("Обновление администратором видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.updateVideoSelectionById(selectionId, request);
    }
}

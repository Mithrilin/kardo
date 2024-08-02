package com.kardoaward.kardo.selection.offline_selection.controller.admin;

import com.kardoaward.kardo.exception.BadRequestException;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/selections/offline")
@Validated
public class OfflineSelectionAdminController {

    @PostMapping
    public OfflineSelectionDto createOfflineSelection(@RequestBody @Valid NewVideoSelectionRequest newVideoSelectionRequest) {

        if (newVideoSelectionRequest.getSelectionEnd().isBefore(newVideoSelectionRequest.getSelectionStart())) {
            log.error("Дата и время начала видео-отбора не может быть после его конца.");
            throw new BadRequestException("Дата и время начала видео-отбора не может быть после его конца.");
        }

        log.info("Добавление администратором нового видео-отбора {}.", newVideoSelectionRequest);
        return videoSelectionService.addVideoSelection(newVideoSelectionRequest);
    }
}

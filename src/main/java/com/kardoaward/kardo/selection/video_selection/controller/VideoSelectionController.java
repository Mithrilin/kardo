package com.kardoaward.kardo.selection.video_selection.controller;

import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/selections/video")
@Validated
public class VideoSelectionController {

    private final VideoSelectionService videoSelectionService;

    @GetMapping("/{selectionId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public VideoSelectionDto getVideoSelectionById(@PathVariable @Positive Long selectionId) {
        log.info("Возвращение видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.getVideoSelectionById(selectionId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<VideoSelectionDto> getVideoSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-отборов.");
        return videoSelectionService.getVideoSelections(from, size);
    }

    @GetMapping("/competitions/{competitionId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<VideoSelectionDto> getVideoSelectionsByGrandCompetitionId(@PathVariable @Positive Long competitionId,
                                                                          @RequestParam(defaultValue = "0")
                                                                          @Min(0) int from,
                                                                          @RequestParam(defaultValue = "10")
                                                                          @Positive int size) {
        log.info("Возвращение списка видео-отборов к гранд-соревнованию с ИД {}.", competitionId);
        return videoSelectionService.getVideoSelectionsByGrandCompetitionId(competitionId, from, size);
    }
}

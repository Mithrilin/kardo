package com.kardoaward.kardo.selection.video_selection.controller;

import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final UserService userService;

    @GetMapping("/{selectionId}")
    public VideoSelectionDto getVideoSelectionById(@PathVariable @Positive Long selectionId) {
        log.info("Возвращение видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.getVideoSelectionById(selectionId);
    }

    @GetMapping
    public List<VideoSelectionDto> getVideoSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-отборов.");
        return videoSelectionService.getVideoSelections(from, size);
    }

    @GetMapping("/contestants/users")
    public List<SelectionDto> getSelectionsByRequestorId(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                         @RequestParam(defaultValue = "0") @Min(0) int from,
                                                         @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка отборов с участием пользователя с ИД {}.", requestorId);
        return selectionService.getSelectionsByRequestorId(requestorId, from, size);
    }

    @GetMapping("/competitions/{competitionId}")
    public List<VideoSelectionDto> getVideoSelectionsByGrandCompetitionId(@PathVariable @Positive Long competitionId,
                                                                          @RequestParam(defaultValue = "0")
                                                                          @Min(0) int from,
                                                                          @RequestParam(defaultValue = "10")
                                                                          @Positive int size) {
        log.info("Возвращение списка видео-отборов к гранд-соревнованию с ИД {}.", competitionId);
        return videoSelectionService.getVideoSelectionsByGrandCompetitionId(competitionId, from, size);
    }
}

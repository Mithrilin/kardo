package com.kardoaward.kardo.video_contest.controller;

import com.kardoaward.kardo.video_contest.model.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.service.VideoContestService;
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
@RequestMapping("/contests")
@Validated
public class VideoContestController {

    private final VideoContestService service;

    @GetMapping("/{contestId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public VideoContestDto getVideoContestById(@PathVariable @Positive Long contestId) {
        log.info("Возвращение видео-конкурса с ИД {}.", contestId);
        return service.getVideoContestById(contestId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<VideoContestDto> getVideoContests(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка видео-конкурсов.");
        return service.getVideoContests(from, size);
    }
}

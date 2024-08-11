package com.kardoaward.kardo.video_contest.controller.admin;

import com.kardoaward.kardo.video_contest.model.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.model.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.model.dto.UpdateVideoContestRequest;
import com.kardoaward.kardo.video_contest.service.VideoContestService;
import com.kardoaward.kardo.video_contest.service.helper.VideoContestValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/admin/contests")
@Validated
public class VideoContestAdminController {

    private final VideoContestService service;

    private final VideoContestValidationHelper videoHelper;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public VideoContestDto createVideoContest(@RequestBody @Valid NewVideoContestRequest newContest) {
        log.info("Добавление администратором нового видео-конкурса {}.", newContest);
        videoHelper.isNewVideoContestDateValid(newContest);
        return service.createVideoContest(newContest);
    }

    @DeleteMapping("/{contestId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteVideoContest(@PathVariable @Positive Long contestId) {
        log.info("Удаление администратором видео-конкурса с ИД {}.", contestId);
        service.deleteVideoContest(contestId);
    }

    @PatchMapping("/{contestId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public VideoContestDto updateVideoContest(@PathVariable @Positive Long contestId,
                                              @RequestBody @Valid UpdateVideoContestRequest request) {
        log.info("Обновление администратором видео-конкурса с ИД {}.", contestId);
        return service.updateVideoContest(contestId, request);
    }
}
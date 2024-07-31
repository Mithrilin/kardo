package com.kardoaward.kardo.online_competition.controller;

import com.kardoaward.kardo.online_competition.model.dto.OnlineCompetitionDto;
import com.kardoaward.kardo.online_competition.service.OnlineCompetitionService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/competitions/online")
@Validated
public class OnlineCompetitionController {

    private final OnlineCompetitionService service;

    @GetMapping("/{competitionId}")
    public OnlineCompetitionDto getOnlineCompetitionById(@PathVariable @Positive Long competitionId) {
        log.info("Возвращение онлайн-соревнования с ИД {}.", competitionId);
        return service.getOnlineCompetitionById(competitionId);
    }

    @GetMapping
    public List<OnlineCompetitionDto> getOnlineCompetitions(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка онлайн-соревнований.");
        return service.getOnlineCompetitions(from, size);
    }
}

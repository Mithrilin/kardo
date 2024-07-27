package com.kardoaward.kardo.offline_competition.controller;

import com.kardoaward.kardo.offline_competition.mapper.OfflineCompetitionMapper;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.OfflineCompetitionDto;
import com.kardoaward.kardo.offline_competition.service.OfflineCompetitionService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/competitions/offline")
@Validated
public class OfflineCompetitionController {

    private final OfflineCompetitionService service;

    private final OfflineCompetitionMapper mapper;

    @GetMapping("/{competitionId}")
    public OfflineCompetitionDto getOfflineCompetitionById(@PathVariable @Positive Long competitionId) {
        log.info("Возвращение администратору информации о оффлайн-соревновании с ИД {}.", competitionId);
        OfflineCompetition returnedOfflineCompetition = service.getOfflineCompetitionById(competitionId);
        return mapper.offlineCompetitionToOfflineCompetitionDto(returnedOfflineCompetition);
    }
}

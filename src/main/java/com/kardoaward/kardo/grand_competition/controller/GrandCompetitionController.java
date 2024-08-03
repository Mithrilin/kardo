package com.kardoaward.kardo.grand_competition.controller;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.service.GrandCompetitionService;
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
@RequestMapping("/competitions")
@Validated
public class GrandCompetitionController {

    private final GrandCompetitionService service;

    private final GrandCompetitionMapper mapper;

    @GetMapping("/{competitionId}")
    public GrandCompetitionDto getGrandCompetitionById(@PathVariable @Positive Long competitionId) {
        log.info("Возвращение гранд-соревнования с ИД {}.", competitionId);
        GrandCompetition returnedGrandCompetition = service.getGrandCompetitionById(competitionId);
        return mapper.grandCompetitionToGrandCompetitionDto(returnedGrandCompetition);
    }

    @GetMapping
    public List<GrandCompetitionDto> getGrandCompetitions(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка гранд-соревнований.");
        List<GrandCompetition> competitions = service.getGrandCompetitions(from, size);
        return mapper.grandCompetitionListToGrandCompetitionDtoList(competitions);
    }
}

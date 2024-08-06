package com.kardoaward.kardo.grand_competition.controller.admin;

import com.kardoaward.kardo.grand_competition.mapper.GrandCompetitionMapper;
import com.kardoaward.kardo.grand_competition.model.GrandCompetition;
import com.kardoaward.kardo.grand_competition.model.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.service.GrandCompetitionService;
import com.kardoaward.kardo.grand_competition.service.helper.GrandCompetitionValidationHelper;
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
@RequestMapping("/admin/competitions")
@Validated
public class GrandCompetitionAdminController {

    private final GrandCompetitionService service;
    /* ToDo
        Перенести все мапперы в сервисы!
     */
    private final GrandCompetitionMapper mapper;

    private final GrandCompetitionValidationHelper grandHelper;

    @PostMapping
    public GrandCompetitionDto createGrandCompetition(@RequestBody @Valid NewGrandCompetitionRequest newCompetition) {
        log.info("Добавление администратором нового гранд-соревнования {}.", newCompetition);
        grandHelper.isNewGrandCompetitionDateValid(newCompetition);
        GrandCompetition competition = mapper.newGrandCompetitionRequestToGrandCompetition(newCompetition);
        GrandCompetition returnedCompetition = service.addGrandCompetition(competition);
        return mapper.grandCompetitionToGrandCompetitionDto(returnedCompetition);
    }

    @DeleteMapping("/{competitionId}")
    public void deleteGrandCompetition(@PathVariable @Positive Long competitionId) {
        log.info("Удаление администратором гранд-соревнования с ИД {}.", competitionId);
        service.deleteGrandCompetition(competitionId);
    }

    @PatchMapping("/{competitionId}")
    public GrandCompetitionDto updateGrandCompetition(@PathVariable @Positive Long competitionId,
                                                      @RequestBody @Valid UpdateGrandCompetitionRequest request) {
        log.info("Обновление администратором гранд-соревнования с ИД {}.", competitionId);
        GrandCompetition updatedCompetition = service.updateGrandCompetition(competitionId, request);
        return mapper.grandCompetitionToGrandCompetitionDto(updatedCompetition);
    }
}

package com.kardoaward.kardo.grand_competition.controller.admin;

import com.kardoaward.kardo.grand_competition.model.dto.NewGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.model.dto.GrandCompetitionDto;
import com.kardoaward.kardo.grand_competition.model.dto.UpdateGrandCompetitionRequest;
import com.kardoaward.kardo.grand_competition.service.GrandCompetitionService;
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
@RequestMapping("/admin/competitions")
@Validated
public class GrandCompetitionAdminController {

    private final GrandCompetitionService service;

    @PostMapping
    @Secured("ADMIN")
    public GrandCompetitionDto createGrandCompetition(@RequestBody @Valid NewGrandCompetitionRequest newCompetition) {
        log.info("Добавление администратором нового гранд-соревнования {}.", newCompetition);
        return service.addGrandCompetition(newCompetition);
    }

    @DeleteMapping("/{competitionId}")
    @Secured("ADMIN")
    public void deleteGrandCompetition(@PathVariable @Positive Long competitionId) {
        log.info("Удаление администратором гранд-соревнования с ИД {}.", competitionId);
        service.deleteGrandCompetition(competitionId);
    }

    @PatchMapping("/{competitionId}")
    @Secured("ADMIN")
    public GrandCompetitionDto updateGrandCompetition(@PathVariable @Positive Long competitionId,
                                                      @RequestBody @Valid UpdateGrandCompetitionRequest request) {
        log.info("Обновление администратором гранд-соревнования с ИД {}.", competitionId);
        return service.updateGrandCompetition(competitionId, request);
    }
}

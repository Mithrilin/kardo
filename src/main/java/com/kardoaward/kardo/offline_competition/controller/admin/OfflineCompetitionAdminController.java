package com.kardoaward.kardo.offline_competition.controller.admin;

import com.kardoaward.kardo.offline_competition.mapper.OfflineCompetitionMapper;
import com.kardoaward.kardo.offline_competition.model.OfflineCompetition;
import com.kardoaward.kardo.offline_competition.model.dto.NewOfflineCompetitionRequest;
import com.kardoaward.kardo.offline_competition.model.dto.OfflineCompetitionDto;
import com.kardoaward.kardo.offline_competition.model.dto.UpdateOfflineCompetitionRequest;
import com.kardoaward.kardo.offline_competition.service.OfflineCompetitionService;
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
@RequestMapping("/admin/competitions/offline")
@Validated
public class OfflineCompetitionAdminController {

    private final OfflineCompetitionService service;

    private final OfflineCompetitionMapper mapper;

    @PostMapping
    public OfflineCompetitionDto createOfflineCompetition(@RequestBody @Valid NewOfflineCompetitionRequest newCompetition) {
        log.info("Добавление администратором нового оффлайн-соревнования {}.", newCompetition);
        OfflineCompetition competition = mapper.newOfflineCompetitionRequestToOfflineCompetition(newCompetition);
        OfflineCompetition returnedCompetition = service.addOfflineCompetition(competition);
        return mapper.offlineCompetitionToOfflineCompetitionDto(returnedCompetition);
    }

    @DeleteMapping("/{competitionId}")
    public void deleteOfflineCompetition(@PathVariable @Positive Long competitionId) {
        log.info("Удаление администратором оффлайн-соревнования с ИД {}.", competitionId);
        service.deleteOfflineCompetition(competitionId);
    }

    @PatchMapping("/{competitionId}")
    public OfflineCompetitionDto updateOfflineCompetition(@PathVariable @Positive Long competitionId,
                                                          @RequestBody UpdateOfflineCompetitionRequest request) {
        log.info("Обновление администратором оффлайн-соревнования с ИД {}.", competitionId);
        OfflineCompetition updatedCompetition = service.updateOfflineCompetition(competitionId, request);
        return mapper.offlineCompetitionToOfflineCompetitionDto(updatedCompetition);
    }
}

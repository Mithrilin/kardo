package com.kardoaward.kardo.online_competition.controller.admin;

import com.kardoaward.kardo.online_competition.model.dto.NewOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.model.dto.OnlineCompetitionDto;
import com.kardoaward.kardo.online_competition.model.dto.UpdateOnlineCompetitionRequest;
import com.kardoaward.kardo.online_competition.service.OnlineCompetitionService;
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
@RequestMapping("/admin/competitions/online")
@Validated
public class OnlineCompetitionAdminController {

    private final OnlineCompetitionService service;

    @PostMapping
    public OnlineCompetitionDto createOnlineCompetition(@RequestBody @Valid NewOnlineCompetitionRequest newCompetition) {
        log.info("Добавление администратором нового онлайн-соревнования {}.", newCompetition);
        return service.createOnlineCompetition(newCompetition);
    }



}

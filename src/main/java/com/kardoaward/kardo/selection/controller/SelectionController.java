package com.kardoaward.kardo.selection.controller;

import com.kardoaward.kardo.selection.model.dto.SelectionDto;
import com.kardoaward.kardo.selection.service.SelectionService;
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
@RequestMapping("/selections")
@Validated
public class SelectionController {

    private final SelectionService selectionService;
    private final UserService userService;

    @GetMapping("/{selectionId}")
    public SelectionDto getSelectionById(@PathVariable @Positive Long selectionId) {
        log.info("Получение отбора с ИД {}.", selectionId);
        return selectionService.getSelectionById(selectionId);
    }

    @GetMapping
    public List<SelectionDto> getSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка отборов.");
        return selectionService.getSelections(from, size);
    }

    @GetMapping("/{selectionId}/contestants")
    public List<UserShortDto> getContestantsBySelectionId(@PathVariable @Positive Long selectionId,
                                                          @RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка участников отбора с ИД {}.", selectionId);
        return userService.getContestantsBySelectionId(selectionId, from, size);
    }

    @GetMapping("/contestants/users")
    public List<SelectionDto> getSelectionsByRequestorId(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                         @RequestParam(defaultValue = "0") @Min(0) int from,
                                                         @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка отборов с участием пользователя с ИД {}.", requestorId);
        return selectionService.getSelectionsByRequestorId(requestorId, from, size);
    }

    @GetMapping("/competitions/offline/{competitionId}")
    public List<SelectionDto> getSelectionsByOfflineCompetitionId(@PathVariable @Positive Long competitionId,
                                                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                                                  @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получение списка отборов к оффлайн-соревнованию с ИД {}.", competitionId);
        return selectionService.getSelectionsByOfflineCompetitionId(competitionId, from, size);
    }
}

package com.kardoaward.kardo.selection.offline_selection.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.selection.offline_selection.model.dto.OfflineSelectionDto;
import com.kardoaward.kardo.selection.offline_selection.service.OfflineSelectionService;
import com.kardoaward.kardo.user.model.dto.UserShortDto;
import com.kardoaward.kardo.user.service.UserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/selections/offline")
@Validated
public class OfflineSelectionController {

    private final OfflineSelectionService offlineSelectionService;
    private final UserService userService;

    @GetMapping("/{selectionId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public OfflineSelectionDto getOfflineSelectionById(@PathVariable @Positive Long selectionId) {
        log.info("Возвращение оффлайн-отбора с ИД {}.", selectionId);
        return offlineSelectionService.getOfflineSelectionById(selectionId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<OfflineSelectionDto> getOfflineSelections(@RequestParam(defaultValue = "0") @Min(0) int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка оффлайн-отборов.");
        return offlineSelectionService.getOfflineSelections(from, size);
    }

    @GetMapping("/{selectionId}/contestants")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<UserShortDto> getContestantsByOfflineSelectionId(@PathVariable @Positive Long selectionId,
                                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Возвращение списка участников оффлайн-отбора с ИД {}.", selectionId);
        return userService.getContestantsByOfflineSelectionId(selectionId, from, size);
    }

    @GetMapping("/contestants")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<OfflineSelectionDto> getOfflineSelectionsByRequestorId(@RequestParam(defaultValue = "0")
                                                                       @Min(0) int from,
                                                                       @RequestParam(defaultValue = "10")
                                                                       @Positive int size) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Возвращение списка оффлайн-отборов с участием пользователя с ИД {}.", requestorId);
        return offlineSelectionService.getOfflineSelectionsByRequestorId(requestorId, from, size);
    }

    @GetMapping("/competitions/{competitionId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<OfflineSelectionDto> getOfflineSelectionsByGrandCompetitionId(@PathVariable
                                                                              @Positive Long competitionId,
                                                                              @RequestParam(defaultValue = "0")
                                                                              @Min(0) int from,
                                                                              @RequestParam(defaultValue = "10")
                                                                              @Positive int size) {
        log.info("Возвращение списка оффлайн-отборов к гранд-соревнованию с ИД {}.", competitionId);
        return offlineSelectionService.getOfflineSelectionsByGrandCompetitionId(competitionId, from, size);
    }
}

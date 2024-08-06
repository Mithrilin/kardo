package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller.admin;

import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateRequest;
import com.kardoaward.kardo.spectator_request.model.dto.update.SpectatorRequestStatusUpdateResult;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/spectators/selection")
@Validated
public class SelectionSpectatorRequestAdminController {

    private final SelectionSpectatorRequestService service;

    @GetMapping("/{spectatorId}")
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestByIdByAdmin(@PathVariable @Positive
                                                                                Long spectatorId) {
        log.info("Возвращение администратору заявки зрителя отбора с ИД {}.", spectatorId);
        return service.getSelectionSpectatorRequestByIdByAdmin(spectatorId);
    }

    @GetMapping("/selections/{selectionId}")
    public List<SelectionSpectatorRequestDto> getSelectionSpectatorRequestByEventId(@PathVariable @Positive
                                                                                    Long selectionId,
                                                                                    @RequestParam(defaultValue = "0")
                                                                                    @Min(0) int from,
                                                                                    @RequestParam(defaultValue = "10")
                                                                                    @Positive int size) {
        log.info("Возвращение администратору списка заявок зрителей к отбору с ИД {}.", selectionId);
        return service.getSelectionSpectatorRequestByEventId(selectionId, from, size);
    }

    @PatchMapping("/selections/{selectionId}")
    public SpectatorRequestStatusUpdateResult updateSelectionSpectatorRequestStatusBySelectionId(
                                                    @PathVariable @Positive Long selectionId,
                                                    @RequestBody @Valid SpectatorRequestStatusUpdateRequest request) {
        log.info("Обновление администратором статуса заявок зрителей к отбору с ИД {}.", selectionId);
        return service.updateSelectionSpectatorRequestStatusBySelectionId(selectionId, request);
    }
}

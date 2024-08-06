package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller;

import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/spectators/selection")
@Validated
public class SelectionSpectatorRequestController {

    private final SelectionSpectatorRequestService service;

    private final SelectionSpectatorRequestValidationHelper helper;

    @PostMapping
    public SelectionSpectatorRequestDto createSelectionSpectatorRequest(@RequestHeader("X-Requestor-Id")
                                                                        Long requestorId,
                                                                        @RequestBody @Valid
                                                                        NewSelectionSpectatorRequest request) {
        log.info("Добавление пользователем с ИД {} новой заявки зрителя отбора {}.", requestorId, request);
        helper.isUserRequester(requestorId, request.getRequesterId());
        return service.addSelectionSpectatorRequest(requestorId, request);
    }

    @DeleteMapping("/{spectatorId}")
    public void deleteSelectionSpectatorRequestById(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                    @PathVariable @Positive Long spectatorId) {
        log.info("Удаление пользователем с ИД {} своей заявки зрителя отбора с ИД {}.", requestorId, spectatorId);
        service.deleteSelectionSpectatorRequestById(requestorId, spectatorId);
    }
}

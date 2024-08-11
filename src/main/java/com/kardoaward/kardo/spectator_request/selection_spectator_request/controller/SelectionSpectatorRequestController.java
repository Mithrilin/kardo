package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller;

import com.kardoaward.kardo.security.MyUserDetails;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public SelectionSpectatorRequestDto createSelectionSpectatorRequest(@RequestBody @Valid
                                                                        NewSelectionSpectatorRequest request) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя отбора {}.", requestorId, request);
        helper.isUserRequester(requestorId, request.getRequesterId());
        return service.addSelectionSpectatorRequest(requestorId, request);
    }

    @DeleteMapping("/{spectatorId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteSelectionSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя отбора с ИД {}.", requestorId, spectatorId);
        service.deleteSelectionSpectatorRequestById(requestorId, spectatorId);
    }

    @GetMapping("/{spectatorId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Возвращение пользователю с ИД {} его заявки зрителя отбора с ИД {}.", requestorId, spectatorId);
        return service.getSelectionSpectatorRequestById(requestorId, spectatorId);
    }
}

package com.kardoaward.kardo.spectator_request.selection_spectator_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.NewSelectionSpectatorRequest;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.model.dto.SelectionSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.SelectionSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.selection_spectator_request.service.helper.SelectionSpectatorRequestValidationHelper;
import com.kardoaward.kardo.user.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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
    @Secured("USER")
    public SelectionSpectatorRequestDto createSelectionSpectatorRequest(@RequestBody @Valid
                                                                        NewSelectionSpectatorRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя отбора {}.", requestor.getId(), request);
        helper.isUserRequesterOrAdmin(requestor, request.getRequesterId());
        return service.addSelectionSpectatorRequest(requestor, request);
    }

    @DeleteMapping("/{spectatorId}")
    @Secured("USER")
    public void deleteSelectionSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя отбора с ИД {}.", requestor.getId(), spectatorId);
        service.deleteSelectionSpectatorRequestById(requestor, spectatorId);
    }

    @GetMapping("/{spectatorId}")
    @Secured({"ADMIN", "USER"})
    public SelectionSpectatorRequestDto getSelectionSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Возвращение заявки зрителя отбора с ИД {}.", spectatorId);
        return service.getSelectionSpectatorRequestById(requestor, spectatorId);
    }
}

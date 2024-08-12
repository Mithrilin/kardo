package com.kardoaward.kardo.spectator_request.event_spectator_request.controller;

import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
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
@RequestMapping("/spectators/event")
@Validated
public class EventSpectatorRequestController {

    private final EventSpectatorRequestService service;

    private final EventSpectatorRequestValidationHelper helper;

    @PostMapping
    @Secured("USER")
    public EventSpectatorRequestDto createEventSpectatorRequest(@RequestBody @Valid NewEventSpectatorRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя мероприятия {}.", requestor.getId(), request);
        helper.isUserRequesterOrAdmin(requestor, request.getRequesterId());
        return service.addEventSpectatorRequest(requestor, request);
    }

    @DeleteMapping("/{spectatorId}")
    @Secured("USER")
    public void deleteEventSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя мероприятия с ИД {}.", requestor.getId(),
                spectatorId);
        service.deleteEventSpectatorRequestById(requestor, spectatorId);
    }

    @GetMapping("/{spectatorId}")
    @Secured({"ADMIN", "USER"})
    public EventSpectatorRequestDto getEventSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Возвращение заявки зрителя мероприятия с ИД {}.", spectatorId);
        return service.getEventSpectatorRequestById(requestor, spectatorId);
    }
}

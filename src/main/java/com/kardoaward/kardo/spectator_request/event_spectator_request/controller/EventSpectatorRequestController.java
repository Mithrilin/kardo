package com.kardoaward.kardo.spectator_request.event_spectator_request.controller;

import com.kardoaward.kardo.security.MyUserDetails;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
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
@RequestMapping("/spectators/event")
@Validated
public class EventSpectatorRequestController {

    private final EventSpectatorRequestService service;

    private final EventSpectatorRequestValidationHelper helper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public EventSpectatorRequestDto createEventSpectatorRequest(@RequestBody @Valid NewEventSpectatorRequest request) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Добавление пользователем с ИД {} новой заявки зрителя мероприятия {}.", requestorId, request);
        helper.isUserRequester(requestorId, request.getRequesterId());
        return service.addEventSpectatorRequest(requestorId, request);
    }

    @DeleteMapping("/{spectatorId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteEventSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Удаление пользователем с ИД {} своей заявки зрителя мероприятия с ИД {}.", requestorId, spectatorId);
        service.deleteEventSpectatorRequestById(requestorId, spectatorId);
    }

    @GetMapping("/{spectatorId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public EventSpectatorRequestDto getEventSpectatorRequestById(@PathVariable @Positive Long spectatorId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Возвращение пользователю с ИД {} его заявки зрителя мероприятия с ИД {}.", requestorId, spectatorId);
        return service.getEventSpectatorRequestById(requestorId, spectatorId);
    }
}

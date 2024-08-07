package com.kardoaward.kardo.spectator_request.event_spectator_request.controller;

import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.EventSpectatorRequestDto;
import com.kardoaward.kardo.spectator_request.event_spectator_request.model.dto.NewEventSpectatorRequest;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.EventSpectatorRequestService;
import com.kardoaward.kardo.spectator_request.event_spectator_request.service.helper.EventSpectatorRequestValidationHelper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public EventSpectatorRequestDto createEventSpectatorRequest(@RequestHeader("X-Requestor-Id") Long requestorId,
                                                                @RequestBody @Valid NewEventSpectatorRequest request) {
        log.info("Добавление пользователем с ИД {} новой заявки зрителя мероприятия {}.", requestorId, request);
        helper.isUserRequester(requestorId, request.getRequesterId());
        return service.addEventSpectatorRequest(requestorId, request);
    }
}
